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

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public interface CloudEvent {
    /**
     * The CloudEvent spec version.
     */
    String version();

    /**
     * List of supported attributes.
     */
    Collection<Attribute> attributes();

    /**
     * Find attribute by id.
     */
    default Optional<Attribute> attribute(String id) {
        return attributes().stream()
            .filter(a -> Objects.equals(id, a.id()))
            .findFirst();
    }

    /**
     * Mandatory find attribute by id.
     */
    default Attribute mandatoryAttribute(String id) {
        return attributes().stream()
            .filter(a -> Objects.equals(id, a.id()))
            .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unable to find attribute with id: " + id));
    }

    interface Attribute {
        /**
         * The ID of the attributes, can be used to look it up.
         */
        String id();

        /**
         * The name of the http header.
         */
        String http();

        /**
         * The name of the json field.
         */
        String json();

        static Attribute simple(String id, String http, String json) {
            return new Attribute() {
                @Override
                public String id() {
                    return id;
                }

                @Override
                public String http() {
                    return http;
                }

                @Override
                public String json() {
                    return json;
                }
            };
        }
    }
}
