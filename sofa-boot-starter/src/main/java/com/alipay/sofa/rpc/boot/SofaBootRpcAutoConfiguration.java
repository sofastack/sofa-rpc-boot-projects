/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.rpc.boot;

import com.alipay.sofa.rpc.boot.config.*;
import com.alipay.sofa.rpc.boot.container.*;
import com.alipay.sofa.rpc.boot.runtime.adapter.helper.ConsumerConfigHelper;
import com.alipay.sofa.rpc.boot.runtime.adapter.helper.ProviderConfigHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
@Configuration
@ComponentScan(value = { "com.alipay.sofa.rpc.boot" })
public class SofaBootRpcAutoConfiguration {
    @Bean
    public SofaBootRpcProperties sofaBootRpcProperties(Environment environment) {
        return new SofaBootRpcProperties(environment);
    }

    @Bean
    public ProviderConfigContainer providerConfigContainer() {
        return new ProviderConfigContainer();
    }

    @Bean
    public FaultToleranceConfigurator faultToleranceConfigurator() {
        return new FaultToleranceConfigurator();
    }

    @Bean
    public ServerConfigContainer serverConfigContainer(SofaBootRpcProperties sofaBootRpcProperties) {
        return new ServerConfigContainer(sofaBootRpcProperties);
    }

    @Bean
    public RegistryConfigContainer registryConfigContainer(SofaBootRpcProperties sofaBootRpcProperties,
                                                           ZookeeperConfigurator zookeeperConfigurator,
                                                           LocalFileConfigurator localFileConfigurator) {
        return new RegistryConfigContainer(sofaBootRpcProperties, zookeeperConfigurator, localFileConfigurator);
    }

    @Bean
    public ConsumerConfigHelper consumerConfigHelper(RegistryConfigContainer registryConfigContainer,
                                                     @Value("${" + SofaBootRpcConfigConstants.APP_NAME + "}") String appName) {
        return new ConsumerConfigHelper(registryConfigContainer, appName);
    }

    @Bean
    public ProviderConfigHelper providerConfigHelper() {
        return new ProviderConfigHelper();
    }

    @Bean
    public ZookeeperConfigurator zookeeperConfigurator(SofaBootRpcProperties sofaBootRpcProperties) {
        return new ZookeeperConfigurator(sofaBootRpcProperties);
    }

    @Bean
    public LocalFileConfigurator localFileConfigurator(SofaBootRpcProperties sofaBootRpcProperties) {
        return new LocalFileConfigurator(sofaBootRpcProperties);
    }

    @Bean
    public ConsumerConfigContainer consumerConfigContainer() {
        return new ConsumerConfigContainer();
    }

    @Bean
    public RpcFilterContainer rpcFilterContainer() {
        return new RpcFilterContainer();
    }
}
