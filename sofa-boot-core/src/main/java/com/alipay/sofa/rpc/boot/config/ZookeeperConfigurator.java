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
package com.alipay.sofa.rpc.boot.config;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * zookeeper 配置
 * <p>
 * 配置格式: com.alipay.sofa.rpc.registry.address=zookeeper://xxx:2181?k1=v1
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class ZookeeperConfigurator {

    /**
     * 注册中心地址
     */
    private String                    address;

    /**
     * 参数
     */
    private final Map<String, String> PARAM_MAP    = new HashMap<String, String>();

    /**
     * 是否已经解析配置
     */
    private boolean                   alreadyParse = false;

    private SofaBootRpcProperties     sofaBootRpcProperties;

    public ZookeeperConfigurator(SofaBootRpcProperties sofaBootRpcProperties) {
        this.sofaBootRpcProperties = sofaBootRpcProperties;
    }

    /**
     * 根据参数 key 读取 value
     *
     * @param key 参数 key
     * @return 参数valule
     */
    String getParamValue(String key) {
        if (StringUtils.hasText(key)) {
            return PARAM_MAP.get(key);
        } else {
            return null;
        }
    }

    /**
     * 获取缓存文件地址
     *
     * @return 缓存文件地址
     */
    public String getFile() {
        String file = PARAM_MAP.get("file");

        if (!StringUtils.hasText(file)) {
            file = SofaBootRpcConfigConstants.REGISTRY_FILE_PATH_DEFAULT;
        }

        return file;

    }

    /**
     * 解析配置 value
     *
     * @param config 配置 value
     */
    void parseConfig(String config) {
        if (StringUtils.hasText(config) && config.startsWith("zookeeper")) {

            String value = config.substring(12);

            if (!value.contains("?")) {
                address = value;
            } else {

                int index = value.lastIndexOf('?');

                address = value.substring(0, index);
                String paramString = value.substring(index + 1);
                parseParam(paramString);

            }
        }
    }

    /**
     * 读取配置 key ,获取其 value 进行解析。
     */
    public void parseConfig() {
        if (!alreadyParse) {
            parseConfig(sofaBootRpcProperties.getRegistryAddress());
            alreadyParse = true;
        }

    }

    private void parseParam(String paramString) {
        if (paramString.contains("&")) {
            String[] paramSplit = paramString.split("&");
            for (String param : paramSplit) {
                parseKeyValue(param);
            }
        } else {
            parseKeyValue(paramString);
        }
    }

    private void parseKeyValue(String kv) {
        String[] kvSplit = kv.split("=");
        String key = kvSplit[0];
        String value = kvSplit[1];
        PARAM_MAP.put(key, value);
    }

    /**
     * Getter method for property <tt>address</tt>.
     *
     * @return property value of address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Setter method for property <tt>address</tt>.
     *
     * @param address value to be assigned to property address
     */
    public void setAddress(String address) {
        this.address = address;
    }
}