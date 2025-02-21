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
package org.apache.camel.component.knative.spi;

import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.Service;

public interface KnativeTransport extends Service {
    /**
     * Create a camel {@link Producer} in place of the original endpoint for a specific protocol.
     *
     * @param endpoint the endpoint for which the producer should be created
     * @param service the service definition containing information about how make reach the target service.
     * @return
     */
    Producer createProducer(
        Endpoint endpoint,
        KnativeEnvironment.KnativeServiceDefinition service);

    /**
     * Create a camel {@link Consumer} in place of the original endpoint for a specific protocol.
     *
     * @param endpoint the endpoint for which the consumer should be created.
     * @param service the service definition containing information about how make the route reachable from knative.
     * @return
     */
    Consumer createConsumer(
        Endpoint endpoint,
        KnativeEnvironment.KnativeServiceDefinition service, Processor processor);
}
