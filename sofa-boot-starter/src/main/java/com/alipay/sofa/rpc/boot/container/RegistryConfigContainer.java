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
package com.alipay.sofa.rpc.boot.container;

import com.alipay.sofa.rpc.boot.common.SofaBootRpcRuntimeException;
import com.alipay.sofa.rpc.boot.config.LocalFileConfigurator;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcProperties;
import com.alipay.sofa.rpc.boot.config.ZookeeperConfigurator;
import com.alipay.sofa.rpc.common.utils.StringUtils;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.registry.Registry;
import com.alipay.sofa.rpc.registry.RegistryFactory;

/**
 * RegistryConfig 工厂
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class RegistryConfigContainer {
    private SofaBootRpcProperties sofaBootRpcProperties;
    private ZookeeperConfigurator zookeeperConfigurator;
    private LocalFileConfigurator localFileConfigurator;

    public RegistryConfigContainer(SofaBootRpcProperties sofaBootRpcProperties,
                                   ZookeeperConfigurator zookeeperConfigurator,
                                   LocalFileConfigurator localFileConfigurator) {
        this.sofaBootRpcProperties = sofaBootRpcProperties;
        this.zookeeperConfigurator = zookeeperConfigurator;
        this.localFileConfigurator = localFileConfigurator;
    }

    /**
     * 单例 local 模式 RegistryConfig
     */
    private volatile RegistryConfig localRegistryConfig;

    /**
     * 单例 zookeeper 模式 RegistryConfig
     */
    private volatile RegistryConfig zookeeperRegistryConfig;

    /**
     * 获取 Registry
     *
     * @return the Registry
     */
    public Registry getRegistry() {
        Registry registry = RegistryFactory.getRegistry(getRegistryConfig());
        registry.init();
        registry.start();

        return registry;
    }

    /**
     * 获取 RegistryConfig
     *
     * @return the RegistryConfig
     * @throws SofaBootRpcRuntimeException SofaBoot运行时异常
     */
    public RegistryConfig getRegistryConfig() throws SofaBootRpcRuntimeException {
        String registryConfig = sofaBootRpcProperties.getRegistryAddress();

        if (StringUtils.isBlank(registryConfig) ||
            registryConfig.startsWith(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_LOCAL)) {

            if (localRegistryConfig == null) {
                synchronized (RegistryConfigContainer.class) {
                    if (localRegistryConfig == null) {
                        localRegistryConfig = createLocalRegistryConfig();
                    }
                }
            }

            return localRegistryConfig;
        } else if (registryConfig.startsWith(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_ZOOKEEPER)) {

            if (zookeeperRegistryConfig == null) {
                synchronized (RegistryConfigContainer.class) {
                    if (zookeeperRegistryConfig == null) {
                        zookeeperRegistryConfig = createZookeeperRegistryConfig();
                    }
                }
            }

            return zookeeperRegistryConfig;
        } else {
            throw new SofaBootRpcRuntimeException("protocol[" + registryConfig + "] is not supported");
        }
    }

    /**
     * 创建 local 协议的 RegistryConfig
     *
     * @return local RegistryConfig
     */
    RegistryConfig createLocalRegistryConfig() {
        localFileConfigurator.parseConfig();

        String filePath = localFileConfigurator.getFile();
        if (StringUtils.isBlank(filePath)) {
            filePath = SofaBootRpcConfigConstants.REGISTRY_FILE_PATH_DEFAULT;
        }

        return new RegistryConfig()
            .setFile(filePath)
            .setProtocol(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_LOCAL);

    }

    /**
     * 创建 zookeeper 协议的 RegistryConfig
     *
     * @return zookeeper RegistryConfig
     */
    public RegistryConfig createZookeeperRegistryConfig() {
        zookeeperConfigurator.parseConfig();

        String address = zookeeperConfigurator.getAddress();
        String filePath = zookeeperConfigurator.getFile();

        return new RegistryConfig()
            .setAddress(address)
            .setFile(filePath)
            .setProtocol(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_ZOOKEEPER);
    }

    /**
     * 移除所有 RegistryConfig
     */
    public void removeAllRegistryConfig() {
        localRegistryConfig = null;
        zookeeperRegistryConfig = null;
    }
}