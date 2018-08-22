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

import com.alipay.sofa.healthcheck.startup.SofaBootMiddlewareAfterReadinessCheckCallback;
import com.alipay.sofa.rpc.boot.config.FaultToleranceConfigurator;
import com.alipay.sofa.rpc.boot.config.LocalFileConfigurator;
import com.alipay.sofa.rpc.boot.config.MeshConfigurator;
import com.alipay.sofa.rpc.boot.config.RegistryConfigureProcessor;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcProperties;
import com.alipay.sofa.rpc.boot.config.ZookeeperConfigurator;
import com.alipay.sofa.rpc.boot.container.ConsumerConfigContainer;
import com.alipay.sofa.rpc.boot.container.ProviderConfigContainer;
import com.alipay.sofa.rpc.boot.container.RegistryConfigContainer;
import com.alipay.sofa.rpc.boot.container.ServerConfigContainer;
import com.alipay.sofa.rpc.boot.context.ApplicationContextClosedListener;
import com.alipay.sofa.rpc.boot.context.ApplicationContextRefreshedListener;
import com.alipay.sofa.rpc.boot.context.SofaBootRpcStartListener;
import com.alipay.sofa.rpc.boot.health.RpcAfterHealthCheckCallback;
import com.alipay.sofa.rpc.boot.runtime.adapter.helper.ConsumerConfigHelper;
import com.alipay.sofa.rpc.boot.runtime.adapter.helper.ProviderConfigHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
@Configuration
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
    public ServerConfigContainer serverConfigContainer(
                                                       SofaBootRpcProperties sofaBootRpcProperties) {
        return new ServerConfigContainer(sofaBootRpcProperties);
    }

    @Bean
    public RegistryConfigContainer registryConfigContainer() {
        return new RegistryConfigContainer();
    }

    @Bean
    public ConsumerConfigHelper consumerConfigHelper(
                                                     @Lazy RegistryConfigContainer registryConfigContainer,
                                                     @Value("${" + SofaBootRpcConfigConstants.APP_NAME + "}") String appName) {
        return new ConsumerConfigHelper(registryConfigContainer, appName);
    }

    @Bean
    public ProviderConfigHelper providerConfigHelper() {
        return new ProviderConfigHelper();
    }

    @Bean
    public ZookeeperConfigurator zookeeperConfigurator() {
        return new ZookeeperConfigurator();
    }

    @Bean
    public LocalFileConfigurator localFileConfigurator() {
        return new LocalFileConfigurator();
    }

    @Bean
    public MeshConfigurator meshConfigurator() {
        return new MeshConfigurator();
    }

    @Bean(name = "registryConfigMap")
    public Map<String, RegistryConfigureProcessor> configureProcessorMap() {
        Map<String, RegistryConfigureProcessor> map = new HashMap<String, RegistryConfigureProcessor>();
        map.put(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_LOCAL, localFileConfigurator());
        map.put(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_ZOOKEEPER, zookeeperConfigurator());
        map.put(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_MESH, meshConfigurator());
        return map;
    }

    @Bean
    public ConsumerConfigContainer consumerConfigContainer() {
        return new ConsumerConfigContainer();
    }

    @Bean
    public ApplicationContextClosedListener applicationContextClosedListener(ProviderConfigContainer providerConfigContainer,
                                                                             ServerConfigContainer serverConfigContainer) {
        return new ApplicationContextClosedListener(providerConfigContainer, serverConfigContainer);
    }

    @Bean
    @ConditionalOnMissingClass({ "com.alipay.sofa.healthcheck.startup.SofaBootMiddlewareAfterReadinessCheckCallback" })
    public ApplicationContextRefreshedListener applicationContextRefreshedListener() {
        return new ApplicationContextRefreshedListener();
    }

    @Bean
    public SofaBootRpcStartListener sofaBootRpcStartListener(
                                                             ProviderConfigContainer providerConfigContainer,
                                                             FaultToleranceConfigurator faultToleranceConfigurator,
                                                             ServerConfigContainer serverConfigContainer,
                                                             RegistryConfigContainer registryConfigContainer
            ) {
        return new SofaBootRpcStartListener(providerConfigContainer, faultToleranceConfigurator, serverConfigContainer,
            registryConfigContainer);
    }

    @Configuration
    @ConditionalOnClass({ SofaBootMiddlewareAfterReadinessCheckCallback.class })
    public static class SofaModuleHealthCheckConfiguration {
        @Bean
        public RpcAfterHealthCheckCallback rpcAfterHealthCheckCallback() {
            return new RpcAfterHealthCheckCallback();
        }
    }

}
