/**
 * Copyright 2016 The Johns Hopkins University Applied Physics Laboratory LLC
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.jhuapl.dorset.demos;

import java.util.Properties;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

import edu.jhuapl.dorset.Application;
import edu.jhuapl.dorset.agent.Agent;
import edu.jhuapl.dorset.agent.AgentRegistry;
import edu.jhuapl.dorset.routing.Router;
import edu.jhuapl.dorset.routing.SingleAgentRouter;

/**
 * Initialize resources for the Dorset api
 * 
 * This uses Jersey's default dependency injection framework.
 */
public class AppInitializer extends ResourceConfig {
    private final Application app;

    /**
     * Create the app and bind it for injection
     */
    public AppInitializer() {
        AgentRegistry registry = new AgentRegistry();
        Agent agent = new UltimateAgent();
        registry.register(agent, new Properties());
        Router router = new SingleAgentRouter(agent.getName());
        app = new Application(registry, router);

        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(app).to(Application.class);
            }
        });
    }
}
