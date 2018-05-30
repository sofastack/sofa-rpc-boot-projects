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
package com.alipay.sofa.rpc.boot.runtime.converter;

import com.alipay.sofa.rpc.boot.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.boot.runtime.binding.BoltBinding;
import com.alipay.sofa.rpc.boot.runtime.binding.H2cBinding;
import com.alipay.sofa.rpc.boot.runtime.binding.RpcBinding;
import com.alipay.sofa.rpc.boot.runtime.binding.RpcBindingType;
import com.alipay.sofa.rpc.boot.runtime.param.BoltBindingParam;
import com.alipay.sofa.rpc.boot.runtime.param.H2cBindingParam;
import com.alipay.sofa.rpc.boot.runtime.param.RpcBindingParam;
import com.alipay.sofa.runtime.api.binding.BindingType;
import com.alipay.sofa.runtime.api.client.param.BindingParam;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author <a href="mailto:zhiyuan.lzy@antfin.com">zhiyuan.lzy</a>
 */
public class H2cBindingConverter extends RpcBindingConverter {
    @Override
    protected RpcBinding createRpcBinding(RpcBindingParam bindingParam, ApplicationContext applicationContext,
                                          boolean inBinding) {
        return new H2cBinding(bindingParam, applicationContext, inBinding);
    }

    @Override
    protected RpcBindingParam createRpcBindingParam() {
        return new H2cBindingParam();
    }

    @Override
    public BindingType supportBindingType() {
        return RpcBindingType.H2C_BINDING_TYPE;
    }

    @Override
    public String supportTagName() {
        return "binding." + SofaBootRpcConfigConstants.RPC_PROTOCOL_H2C;
    }
}