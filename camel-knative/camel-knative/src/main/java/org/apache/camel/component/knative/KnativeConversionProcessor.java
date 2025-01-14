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

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.knative.spi.Knative;

/**
 * Converts objects prior to serializing them to external endpoints or channels
 */
public class KnativeConversionProcessor implements Processor {

    private boolean enabled;

    public KnativeConversionProcessor(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        if (enabled) {
            Object body = exchange.getIn().getBody();
            if (body != null) {
                byte[] newBody = Knative.MAPPER.writeValueAsBytes(body);
                exchange.getIn().setBody(newBody);
                exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "application/json");
            }
        }
    }
}
