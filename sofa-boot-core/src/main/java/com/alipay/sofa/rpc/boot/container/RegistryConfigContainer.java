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
import com.alipay.sofa.rpc.boot.config.RegistryConfigureProcessor;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcProperties;
import com.alipay.sofa.rpc.common.utils.StringUtils;
import com.alipay.sofa.rpc.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * RegistryConfig 工厂
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class RegistryConfigContainer {

    private static final String                     GLOBAL_REGISTRY   = "DEFAULT";

    @Autowired
    private SofaBootRpcProperties                   sofaBootRpcProperties;

    @Resource(name = "registryConfigMap")
    private Map<String, RegistryConfigureProcessor> registryConfigMap = new HashMap<String, RegistryConfigureProcessor>(
                                                                          4);

    public RegistryConfigContainer() {
    }

    public RegistryConfig getRegistryConfig(String registryAlias) throws SofaBootRpcRuntimeException {

        String registryProtocol;
        String registryAddress = null;
        if (StringUtils.isEmpty(registryAlias) || GLOBAL_REGISTRY.equalsIgnoreCase(registryAlias)) {
            registryAddress = sofaBootRpcProperties.getRegistryAddress();
        } else {
            registryAddress = sofaBootRpcProperties.getRegistries().get(registryAlias);
        }

        if (StringUtils.isBlank(registryAddress)) {
            registryProtocol = SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_LOCAL;
        } else {
            final int endIndex = registryAddress.indexOf(":");
            if (endIndex != -1) {
                registryProtocol = registryAddress.substring(0, endIndex);
            }
            else {
                registryProtocol = registryAlias;
            }
        }

        if (registryConfigMap.get(registryProtocol) != null) {
            RegistryConfigureProcessor registryConfigureProcessor = registryConfigMap.get(registryProtocol);
            RegistryConfig registryConfig = registryConfigureProcessor.buildFromAddress(registryAddress);
            return registryConfig;
        } else {
            throw new SofaBootRpcRuntimeException("registry config [" + registryAddress + "] is not supported");
        }
    }

    /**
     * 获取 RegistryConfig
     *
     * @return the RegistryConfig
     * @throws SofaBootRpcRuntimeException SofaBoot运行时异常
     */
    public RegistryConfig getRegistryConfig() throws SofaBootRpcRuntimeException {

        return getRegistryConfig(GLOBAL_REGISTRY);
    }

    /**
     * 移除所有 RegistryConfig
     */
    public void removeAllRegistryConfig() {
        registryConfigMap.clear();
    }

    public Map<String, RegistryConfigureProcessor> getRegistryConfigMap() {
        return registryConfigMap;
    }

    public void setRegistryConfigMap(Map<String, RegistryConfigureProcessor> registryConfigMap) {
        this.registryConfigMap = registryConfigMap;
    }
}