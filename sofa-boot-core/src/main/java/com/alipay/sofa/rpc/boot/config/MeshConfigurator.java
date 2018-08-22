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

import com.alipay.sofa.rpc.common.utils.StringUtils;
import com.alipay.sofa.rpc.config.RegistryConfig;

/**
 * mesh注册中心配置
 * 配置格式：com.alipay.sofa.rpc.registries.mesh=mesh://127.0.0.1:12220
 *
 * @author liangen
 * @version $Id: LocalFileConfigurator.java, v 0.1 2018年04月17日 下午2:44 liangen Exp $
 */
public class MeshConfigurator implements RegistryConfigureProcessor {

    public static final String HTTP = "http://";

    public MeshConfigurator() {
    }

    /**
     * 读取配置 key ,获取其 value 进行解析。
     */
    public String parseConfig(String config) {
        String address = null;

        if (StringUtils.isNotEmpty(config) && config.startsWith(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_MESH)) {
            final String meshProtocol = SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_MESH + "://";
            String value = config.substring(meshProtocol.length());
            if (!value.contains("?")) {
                address = value;
            } else {
                int index = value.lastIndexOf('?');
                address = value.substring(0, index);
            }
        }

        return address;
    }

    @Override
    public RegistryConfig buildFromAddress(String address) {
        String meshAddress = parseConfig(address);

        meshAddress = HTTP + meshAddress;
        return new RegistryConfig()
            .setAddress(meshAddress)
            .setProtocol(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_MESH);
    }

}