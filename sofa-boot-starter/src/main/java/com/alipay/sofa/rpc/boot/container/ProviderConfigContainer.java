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

import com.alipay.sofa.rpc.boot.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.boot.log.SofaBootRpcLoggerFactory;
import com.alipay.sofa.rpc.boot.runtime.binding.RpcBinding;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.registry.Registry;
import com.alipay.sofa.runtime.spi.binding.Contract;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ProviderConfig持有者.维护编程界面级别的RPC组件。
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class ProviderConfigContainer {
    private static final Logger                                LOGGER                = SofaBootRpcLoggerFactory
                                                                                         .getLogger(ProviderConfigContainer.class);

    /**
     * 是否允许发布ProviderConfig
     */
    private static boolean                                     allowPublish          = false;

    /**
     * ProviderConfig 缓存
     */
    private static final ConcurrentMap<String, ProviderConfig> RPC_SERVICE_CONTAINER = new ConcurrentHashMap<String, ProviderConfig>(
                                                                                         256);

    /**
     * 增加 ProviderConfig
     * @param key 唯一id
     * @param providerConfig the ProviderConfig
     */
    public static void addProviderConfig(String key, ProviderConfig providerConfig) {
        if (providerConfig != null) {
            if (RPC_SERVICE_CONTAINER.containsKey(key)) {
                LOGGER.warn("已经存在相同的服务及协议,key[" + key + "];protocol[" + providerConfig.getServer().get(0) + "]");
            } else {
                RPC_SERVICE_CONTAINER.put(key, providerConfig);
            }
        }
    }

    /**
     * 获取 ProviderConfig
     * @param key 唯一id
     * @return the ProviderConfig
     */
    public static ProviderConfig getProviderConfig(String key) {
        return RPC_SERVICE_CONTAINER.get(key);
    }

    /**
     * 移除 ProviderConfig
     * @param key 唯一id
     */
    public static void removeProviderConfig(String key) {
        RPC_SERVICE_CONTAINER.remove(key);
    }

    /**
     * 获取缓存的所有 ProviderConfig
     * @return 所有 ProviderConfig
     */
    public static Collection<ProviderConfig> getAllProviderConfig() {
        return RPC_SERVICE_CONTAINER.values();
    }

    /**
     * 发布所有 ProviderConfig 元数据信息到注册中心
     * @param registry 注册中心
     */
    public static void publishAllProviderConfig(Registry registry) {
        for (ProviderConfig providerConfig : ProviderConfigContainer.getAllProviderConfig()) {

            ServerConfig serverConfig = (ServerConfig) providerConfig.getServer().get(0);
            if (!serverConfig.getProtocol().equalsIgnoreCase(SofaBootRpcConfigConstants.RPC_PROTOCOL_DUBBO)) {
                providerConfig.setRegister(true);
                registry.register(providerConfig);

                LOGGER.info("service published.  interfaceid[" + providerConfig.getInterfaceId() + "]; protocl[" +
                    serverConfig.getProtocol() + "]");
            }
        }
    }

    /**
     * export所有 Dubbo 类型的 ProviderConfig
     */
    public static void exportAllDubboProvideConfig() {
        for (ProviderConfig providerConfig : ProviderConfigContainer.getAllProviderConfig()) {

            ServerConfig serverConfig = (ServerConfig) providerConfig.getServer().get(0);
            if (serverConfig.getProtocol().equalsIgnoreCase(SofaBootRpcConfigConstants.RPC_PROTOCOL_DUBBO)) {
                providerConfig.setRegister(true);
                providerConfig.export();

                LOGGER.info("service published.  interfaceid[" + providerConfig.getInterfaceId() + "]; protocl[" +
                    serverConfig.getProtocol() + "]");
            }
        }
    }

    /**
     * unExport所有的 ProviderConfig
     */
    public static void unExportAllProviderConfig() {
        for (ProviderConfig providerConfig : ProviderConfigContainer.getAllProviderConfig()) {
            providerConfig.unExport();
        }

    }

    /**
     * 是否允许发布 ProviderConfig 元数据信息
     * @return
     */
    public static boolean isAllowPublish() {
        return allowPublish;
    }

    /**
     * 设置是否允许发布 ProviderConfig 元数据信息
     * @param allowPublish 是否允许发布 ProviderConfig 元数据信息
     */
    public static void setAllowPublish(boolean allowPublish) {
        ProviderConfigContainer.allowPublish = allowPublish;
    }

    /**
     * 创建唯一Id
     * @param contract the Contract
     * @param binding the RpcBinding
     * @return 唯一id
     */
    public static String createUniqueName(Contract contract, RpcBinding binding) {
        String uniqueId = "";
        String version = ":1.0";
        String protocol = "";
        if (StringUtils.hasText(contract.getUniqueId())) {
            uniqueId = ":" + contract.getUniqueId();
        }
        if (StringUtils.hasText(contract.getProperty("version"))) {
            version = ":" + contract.getProperty("version");
        }
        if (StringUtils.hasText(binding.getBindingType().getType())) { //dubbo can not merge to bolt
            protocol = ":" + binding.getBindingType().getType();
        }

        return new StringBuffer(contract.getInterfaceType().getName()).append(version)
            .append(uniqueId).append(protocol).toString();
    }

}
