/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.knative;

import org.apache.camel.CamelContext;
import org.apache.camel.component.knative.spi.Knative;
import org.apache.camel.component.knative.spi.KnativeEnvironment;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.apache.camel.component.knative.spi.KnativeEnvironment.mandatoryLoadFromResource;
import static org.assertj.core.api.Assertions.assertThat;

public class KnativeComponentTest {

    private CamelContext context;

    // **************************
    //
    // Setup
    //
    // **************************

    @BeforeEach
    public void before() {
        this.context = new DefaultCamelContext();
    }

    @AfterEach
    public void after() throws Exception {
        if (this.context != null) {
            this.context.stop();
        }
    }

    // **************************
    //
    // Common Tests
    //
    // **************************

    @Test
    void testLoadEnvironment() throws Exception {
        KnativeEnvironment env = mandatoryLoadFromResource(context, "classpath:/environment.json");

        assertThat(env.stream()).hasSize(3);
        assertThat(env.stream()).anyMatch(s -> s.getType() == Knative.Type.channel);
        assertThat(env.stream()).anyMatch(s -> s.getType() == Knative.Type.endpoint);

        KnativeComponent component = context.getComponent("knative", KnativeComponent.class);
        component.setEnvironment(env);

        //
        // Channels
        //
        {
            KnativeEndpoint endpoint = context.getEndpoint("knative:channel/c1", KnativeEndpoint.class);
            assertThat(endpoint.lookupServiceDefinition("c1", Knative.EndpointKind.source)).isPresent();
            assertThat(endpoint.lookupServiceDefinition("e1", Knative.EndpointKind.source)).isNotPresent();
        }

        //
        // Endpoints
        //
        {
            KnativeEndpoint endpoint = context.getEndpoint("knative:endpoint/e1", KnativeEndpoint.class);
            assertThat(endpoint.lookupServiceDefinition("e1", Knative.EndpointKind.source)).isPresent();
            assertThat(endpoint.lookupServiceDefinition("c1", Knative.EndpointKind.source)).isNotPresent();
        }
    }
}
