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
package com.alipay.sofa.rpc.core.factory;

import com.alipay.sofa.infra.constants.CommonMiddlewareConstants;
import com.alipay.sofa.rpc.common.SofaBootRpcRuntimeException;
import com.alipay.sofa.rpc.config.ApplicationConfig;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.config.MethodConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.SofaBootRpcConfig;
import com.alipay.sofa.rpc.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.core.invoke.SofaResponseCallback;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.register.binding.RpcBinding;
import com.alipay.sofa.rpc.register.binding.RpcBindingMethodInfo;
import com.alipay.sofa.rpc.register.param.RpcBindingParam;
import com.alipay.sofa.runtime.spi.binding.Contract;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * ConsumerConfig 工厂。
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class ConsumerConfigFactory {

    /**
     * 获取 ConsumerConfig
     * @param contract the Contract
     * @param binding the RpcBinding
     * @return the ConsumerConfig
     */
    public static ConsumerConfig getConsumerConfig(Contract contract, RpcBinding binding) {
        RpcBindingParam param = binding.getRpcBindingParam();

        String appName = SofaBootRpcConfig.getProperty(CommonMiddlewareConstants.APP_NAME_KEY);
        String id = binding.getBeanId();
        String interfaceId = contract.getInterfaceType().getName();
        String uniqueId = contract.getUniqueId();

        Integer timeout = param.getTimeout();
        Integer retries = param.getRetries();
        String type = param.getType();
        Integer addressWaitTime = param.getAddressWaitTime();
        Object callbackHandler = param.getCallbackHandler();
        String genericInterface = param.getGenericInterface();

        List<Filter> filters = param.getFilters();
        List<MethodConfig> methodConfigs = convertToMethodConfig(param.getMethodInfos());
        String targetUrl = param.getTargetUrl();

        RegistryConfig registryConfig = RegistryConfigFactory.getRegistryConfig();

        ConsumerConfig consumerConfig = new ConsumerConfig();
        if (StringUtils.hasText(appName)) {
            consumerConfig.setApplication(new ApplicationConfig().setAppName(appName));
        }
        if (StringUtils.hasText(id)) {
            consumerConfig.setId(id);
        }
        if (StringUtils.hasText(genericInterface)) {
            consumerConfig.setGeneric(true);
            consumerConfig.setInterfaceId(genericInterface);
        } else if (StringUtils.hasText(interfaceId)) {
            consumerConfig.setInterfaceId(interfaceId);
        }
        if (StringUtils.hasText(uniqueId)) {
            consumerConfig.setUniqueId(uniqueId);
        }
        if (timeout != null) {
            consumerConfig.setTimeout(timeout);
        }
        if (retries != null) {
            consumerConfig.setRetries(retries);
        }
        if (StringUtils.hasText(type)) {
            consumerConfig.setInvokeType(type);
        }
        if (addressWaitTime != null) {
            consumerConfig.setAddressWait(addressWaitTime);
        }
        if (callbackHandler != null) {
            if (callbackHandler instanceof SofaResponseCallback) {
                consumerConfig.setOnReturn((SofaResponseCallback) callbackHandler);
            } else {
                throw new SofaBootRpcRuntimeException("callback handler must implement SofaResponseCallback [" +
                    callbackHandler + "]");
            }
        }
        if (!CollectionUtils.isEmpty(filters)) {
            consumerConfig.setFilterRef(filters);
        }
        if (!CollectionUtils.isEmpty(methodConfigs)) {
            consumerConfig.setMethods(methodConfigs);
        }
        if (StringUtils.hasText(targetUrl)) {
            consumerConfig.setDirectUrl(targetUrl);
            consumerConfig.setSubscribe(false);
            consumerConfig.setRegister(false);
        }
        consumerConfig.setRegistry(registryConfig);

        String protocol = binding.getBindingType().getType();
        consumerConfig.setBootstrap(protocol);

        if (protocol.equals(SofaBootRpcConfigConstants.RPC_PROTOCOL_DUBBO)) {
            consumerConfig.setInJVM(false);
        }

        return consumerConfig.setProtocol(protocol);
    }

    private static List<MethodConfig> convertToMethodConfig(List<RpcBindingMethodInfo> methodInfos) {
        List<MethodConfig> methodConfigs = new ArrayList<MethodConfig>();

        if (!CollectionUtils.isEmpty(methodInfos)) {

            for (RpcBindingMethodInfo info : methodInfos) {

                String name = info.getName();
                Integer timeout = info.getTimeout();
                Integer retries = info.getRetries();
                String type = info.getType();
                Object callbackHandler = info.getCallbackHandler();

                MethodConfig methodConfig = new MethodConfig();
                methodConfig.setName(name);
                if (timeout != null) {
                    methodConfig.setTimeout(timeout);
                }
                if (retries != null) {
                    methodConfig.setRetries(retries);
                }
                if (StringUtils.hasText(type)) {
                    methodConfig.setInvokeType(type);
                }
                if (callbackHandler != null) {
                    if (callbackHandler instanceof SofaResponseCallback) {
                        methodConfig.setOnReturn((SofaResponseCallback) callbackHandler);
                    } else {
                        throw new SofaBootRpcRuntimeException("callback handler must implement SofaResponseCallback [" +
                            callbackHandler + "]");
                    }
                }

                methodConfigs.add(methodConfig);
            }

        }

        return methodConfigs;
    }
}