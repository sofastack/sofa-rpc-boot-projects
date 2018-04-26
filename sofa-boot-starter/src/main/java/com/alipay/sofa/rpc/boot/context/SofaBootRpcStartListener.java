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
package com.alipay.sofa.rpc.boot.context;

import com.alipay.sofa.rpc.boot.config.FaultToleranceConfigurator;
import com.alipay.sofa.rpc.boot.container.ProviderConfigContainer;
import com.alipay.sofa.rpc.boot.container.RegistryConfigContainer;
import com.alipay.sofa.rpc.boot.container.ServerConfigContainer;
import com.alipay.sofa.rpc.boot.context.event.SofaBootRpcStartEvent;
import com.alipay.sofa.rpc.registry.Registry;
import org.springframework.context.ApplicationListener;

/**
 * {@link SofaBootRpcStartEvent) 事件监听器.
 * 加载并初始化 SOFABoot RPC 需要的配置。
 * 开启服务器并发布服务元数据信息。
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class SofaBootRpcStartListener implements ApplicationListener<SofaBootRpcStartEvent> {
    private final ProviderConfigContainer    providerConfigContainer;
    private final FaultToleranceConfigurator faultToleranceConfigurator;
    private final ServerConfigContainer      serverConfigContainer;
    private final RegistryConfigContainer    registryConfigContainer;

    public SofaBootRpcStartListener(
                                    ProviderConfigContainer providerConfigContainer,
                                    FaultToleranceConfigurator faultToleranceConfigurator,
                                    ServerConfigContainer serverConfigContainer,
                                    RegistryConfigContainer registryConfigContainer) {
        this.providerConfigContainer = providerConfigContainer;
        this.faultToleranceConfigurator = faultToleranceConfigurator;
        this.serverConfigContainer = serverConfigContainer;
        this.registryConfigContainer = registryConfigContainer;
    }

    @Override
    public void onApplicationEvent(SofaBootRpcStartEvent event) {
        //start fault tolerance
        faultToleranceConfigurator.startFaultTolerance();

        //start server
        serverConfigContainer.startServers();

        //init registry
        Registry registry = registryConfigContainer.getRegistry();

        //set allow all publish
        providerConfigContainer.setAllowPublish(true);

        //register registry
        providerConfigContainer.publishAllProviderConfig(registry);

        //export dubbo
        providerConfigContainer.exportAllDubboProvideConfig();
    }
}