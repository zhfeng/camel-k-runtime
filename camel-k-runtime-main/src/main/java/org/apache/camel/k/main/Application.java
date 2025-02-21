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
package org.apache.camel.k.main;

import java.util.ServiceLoader;

import org.apache.camel.k.Runtime;
import org.apache.camel.k.support.PropertiesSupport;

public final class Application {
    static {
        //
        // Configure the logging subsystem log4j2 using a subset of spring boot
        // conventions:
        //
        //    logging.level.${nane} = OFF|FATAL|ERROR|WARN|INFO|DEBUG|TRACE|ALL
        //
        // We now support setting the logging level only
        //
        ApplicationSupport.configureLogging();
    }

    private Application() {
    }
    
    public static void main(String[] args) throws Exception {
        ApplicationRuntime runtime = new ApplicationRuntime();
        runtime.setProperties(PropertiesSupport.loadProperties());
        runtime.addListeners(ServiceLoader.load(Runtime.Listener.class));
        runtime.run();
    }
}
