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

import com.alipay.sofa.rpc.boot.common.SofaBootRpcParserUtil;
import com.alipay.sofa.rpc.boot.common.SofaBootRpcRuntimeException;
import com.alipay.sofa.rpc.boot.common.SofaBootRpcSpringUtil;
import com.alipay.sofa.rpc.boot.container.RpcFilterContainer;
import com.alipay.sofa.rpc.boot.runtime.binding.RpcBinding;
import com.alipay.sofa.rpc.boot.runtime.binding.RpcBindingMethodInfo;
import com.alipay.sofa.rpc.boot.runtime.binding.RpcBindingXmlConstants;
import com.alipay.sofa.rpc.boot.runtime.param.RpcBindingParam;
import com.alipay.sofa.rpc.filter.ExcludeFilter;
import com.alipay.sofa.rpc.filter.Filter;
import com.alipay.sofa.rpc.server.UserThreadPool;
import com.alipay.sofa.runtime.spi.service.BindingConverter;
import com.alipay.sofa.runtime.spi.service.BindingConverterContext;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析 XML配置或者 {@link RpcBindingParam} 为 {@link RpcBinding}
 *
 * @author <a href="mailto:caojie.cj@antfin.com">CaoJie</a>
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public abstract class RpcBindingConverter implements BindingConverter<RpcBindingParam, RpcBinding> {

    private static final String FILTER_SEPERATOR_SYMBOL     = ",";
    private static final char   EXCLUDE_FILTER_BEGIN_SYMBOL = '!';

    /**
     * convert {@link RpcBindingParam} to concrete {@link RpcBinding}
     *
     * @param bindingParam            binding parameter
     * @param bindingConverterContext binding converter context
     * @return RpcBinding Object
     */
    @Override
    public RpcBinding convert(RpcBindingParam bindingParam, BindingConverterContext bindingConverterContext) {
        RpcBinding binding = createRpcBinding(bindingParam, bindingConverterContext.getApplicationContext(),
            bindingConverterContext.isInBinding());

        setCallback(bindingParam, bindingConverterContext);

        binding.setAppName(bindingConverterContext.getAppName());
        binding.setBeanId(bindingConverterContext.getBeanId());
        return binding;
    }

    /**
     * convert xml Element to concrete {@link RpcBinding}
     *
     * @param element                 xml Element
     * @param bindingConverterContext binding converter context
     * @return RpcBinding Object
     */
    @Override
    public RpcBinding convert(Element element, BindingConverterContext bindingConverterContext) {
        RpcBindingParam param = createRpcBindingParam();
        //global-attrs,filter,method,route
        Element globalAttrsElement = DomUtils
            .getChildElementByTagName(element, RpcBindingXmlConstants.TAG_GLOBAL_ATTRS);
        Element routeElement = DomUtils.getChildElementByTagName(element, RpcBindingXmlConstants.TAG_ROUTE);
        List<Element> methodElements = DomUtils.getChildElementsByTagName(element, RpcBindingXmlConstants.TAG_METHOD);

        parseGlobalAttrs(globalAttrsElement, param, bindingConverterContext);
        parseFilter(globalAttrsElement, param, bindingConverterContext);
        parseMethod(methodElements, param);
        parseRoute(routeElement, param);

        return convert(param, bindingConverterContext);

    }

    /**
     * 创建 RpcBinding
     *
     * @param bindingParam       the RpcBindingParam
     * @param applicationContext spring 上下文
     * @param inBinding          是否是服务引用
     * @return the RpcBinding
     */
    protected abstract RpcBinding createRpcBinding(RpcBindingParam bindingParam,
                                                   ApplicationContext applicationContext, boolean inBinding);

    /**
     * 创建 RpcBindingParam
     *
     * @return the RpcBindingParam
     */
    protected abstract RpcBindingParam createRpcBindingParam();

    private void parseMethod(List<Element> elements, RpcBindingParam param) {

        if (CollectionUtils.isEmpty(elements)) {
            return;
        }

        List<RpcBindingMethodInfo> boltBindingMethodInfos = new ArrayList<RpcBindingMethodInfo>();

        for (Element element : elements) {
            if (element.getNodeType() == Node.ELEMENT_NODE &&
                element.getLocalName().equals(RpcBindingXmlConstants.TAG_METHOD)) {

                String name = element.getAttribute(RpcBindingXmlConstants.TAG_NAME);
                Integer timeout = SofaBootRpcParserUtil.parseInteger(element
                    .getAttribute(RpcBindingXmlConstants.TAG_TIMEOUT));
                Integer retries = SofaBootRpcParserUtil.parseInteger(element
                    .getAttribute(RpcBindingXmlConstants.TAG_RETRIES));
                String type = element.getAttribute(RpcBindingXmlConstants.TAG_TYPE);

                RpcBindingMethodInfo boltBindingMethodInfo = new RpcBindingMethodInfo();
                if (StringUtils.hasText(name)) {
                    boltBindingMethodInfo.setName(name);
                }
                if (timeout != null) {
                    boltBindingMethodInfo.setTimeout(timeout);
                }
                if (retries != null) {
                    boltBindingMethodInfo.setRetries(retries);
                }
                if (StringUtils.hasText(type)) {
                    boltBindingMethodInfo.setType(type);
                }

                if (type.equalsIgnoreCase(RpcBindingXmlConstants.TYPE_CALLBACK)) {
                    String callbackRef = element.getAttribute(RpcBindingXmlConstants.TAG_CALLBACK_REF);
                    String callbackClass = element.getAttribute(RpcBindingXmlConstants.TAG_CALLBACK_CLASS);

                    boltBindingMethodInfo.setCallbackRef(callbackRef);
                    boltBindingMethodInfo.setCallbackClass(callbackClass);
                }

                boltBindingMethodInfos.add(boltBindingMethodInfo);
            }
        }

        param.setMethodInfos(boltBindingMethodInfos);

    }

    private void parseGlobalAttrs(Element element, RpcBindingParam param,
                                  BindingConverterContext bindingConverterContext) {
        if (element == null) {
            return;
        }

        Integer timeout = SofaBootRpcParserUtil.parseInteger(element.getAttribute(RpcBindingXmlConstants.TAG_TIMEOUT));
        Integer addressWaitTime = SofaBootRpcParserUtil.parseInteger(element
            .getAttribute(RpcBindingXmlConstants.TAG_ADDRESS_WAIT_TIME));
        Integer connectTimeout = SofaBootRpcParserUtil.parseInteger(element
            .getAttribute(RpcBindingXmlConstants.TAG_CONNECT_TIMEOUT));
        Integer retries = SofaBootRpcParserUtil.parseInteger(element.getAttribute(RpcBindingXmlConstants.TAG_RETRIES));
        String type = element.getAttribute(RpcBindingXmlConstants.TAG_TYPE);
        String callbackClass = element.getAttribute(RpcBindingXmlConstants.TAG_CALLBACK_CLASS);
        String callbackRef = element.getAttribute(RpcBindingXmlConstants.TAG_CALLBACK_REF);
        Integer weight = SofaBootRpcParserUtil.parseInteger(element.getAttribute(RpcBindingXmlConstants.TAG_WEIGHT));
        Integer warmUpTime = SofaBootRpcParserUtil.parseInteger(element
            .getAttribute(RpcBindingXmlConstants.TAG_WARMUP_TIME));
        Integer warmUpWeight = SofaBootRpcParserUtil
            .parseInteger(element.getAttribute(RpcBindingXmlConstants.TAG_WARMUP_WEIGHT));
        Object treadPoolRef = SofaBootRpcSpringUtil.getSpringBean(
            element.getAttribute(RpcBindingXmlConstants.TAG_THREAD_POOL_REF),
            bindingConverterContext.getApplicationContext(), bindingConverterContext.getAppClassLoader(),
            bindingConverterContext.getAppName());
        String genericInterface = element.getAttribute(RpcBindingXmlConstants.TAG_GENERIC_INTERFACE);

        if (timeout != null) {
            param.setTimeout(timeout);
        }
        if (addressWaitTime != null) {
            param.setAddressWaitTime(addressWaitTime);
        }
        if (connectTimeout != null) {
            param.setConnectTimeout(connectTimeout);
        }
        if (retries != null) {
            param.setRetries(retries);
        }
        if (StringUtils.hasText(type)) {
            param.setType(type);
        }
        if (StringUtils.hasText(callbackClass)) {
            param.setCallbackClass(callbackClass);
        }
        if (StringUtils.hasText(callbackRef)) {
            param.setCallbackRef(callbackRef);
        }
        if (weight != null) {
            param.setWeight(weight);
        }
        if (warmUpTime != null) {
            param.setWarmUpTime(warmUpTime);
        }
        if (warmUpWeight != null) {
            param.setWarmUpWeight(warmUpWeight);
        }
        if (treadPoolRef != null) {
            param.setUserThreadPool((UserThreadPool) treadPoolRef);
        }
        if (StringUtils.hasText(genericInterface)) {
            param.setGenericInterface(genericInterface);
        }
    }

    private void parseFilter(Element element, RpcBindingParam param, BindingConverterContext bindingConverterContext) {
        List<Filter> filters = new ArrayList<Filter>(RpcFilterContainer.getInstance().getFilters(
            bindingConverterContext.getApplicationContext()));

        if (element != null) {
            List<String> filterNames = new ArrayList<String>();

            String filterStrs = element.getAttribute(RpcBindingXmlConstants.TAG_FILTER);
            if (StringUtils.hasText(filterStrs)) {

                String[] subFilter = filterStrs.split(FILTER_SEPERATOR_SYMBOL);
                for (String subfilterName : subFilter) {
                    if (StringUtils.hasText(subfilterName)) {

                        if (subfilterName.charAt(0) == EXCLUDE_FILTER_BEGIN_SYMBOL) {
                            String realFilterName = subfilterName.substring(1);
                            if (StringUtils.hasText(realFilterName)) {

                                filters.add(new ExcludeFilter(realFilterName));
                            }
                        } else {
                            filterNames.add(subfilterName);
                        }
                    }
                }
            }

            if (!CollectionUtils.isEmpty(filterNames)) {
                for (String filterName : filterNames) {
                    Object filter = bindingConverterContext.getApplicationContext().getBean(filterName);
                    if (filter instanceof Filter) {
                        filters.add((Filter) filter);
                    } else {
                        throw new SofaBootRpcRuntimeException("filter name[" + filterName + "] is not ref a Filter.");
                    }
                }
            }
        }

        param.setFilters(filters);
    }

    private void parseRoute(Element routeElement, RpcBindingParam param) {
        if (routeElement == null) {
            return;
        }

        String targetUrl = routeElement.getAttribute(RpcBindingXmlConstants.TAG_TARGET_URL);
        if (StringUtils.hasText(targetUrl)) {
            param.setTargetUrl(targetUrl);
        }

    }

    private void setCallback(RpcBindingParam bindingParam, BindingConverterContext bindingConverterContext) {
        //global
        if (bindingParam.getCallbackHandler() == null) {
            Object globalCallbackHandler = SofaBootRpcSpringUtil.getSpringBean(bindingParam.getCallbackRef(),
                bindingParam.getCallbackClass(),
                bindingConverterContext.getApplicationContext(), bindingConverterContext.getAppClassLoader(),
                bindingConverterContext.getAppName());

            if (globalCallbackHandler != null) {
                bindingParam.setCallbackHandler(globalCallbackHandler);
            }
        }

        //method
        if (!CollectionUtils.isEmpty(bindingParam.getMethodInfos())) {
            for (RpcBindingMethodInfo methodInfo : bindingParam.getMethodInfos()) {
                Object methodCallbackHandler = methodInfo.getCallbackHandler();
                if (methodCallbackHandler == null) {
                    methodCallbackHandler = SofaBootRpcSpringUtil.getSpringBean(methodInfo.getCallbackRef(),
                        methodInfo.getCallbackClass(), bindingConverterContext.getApplicationContext(),
                        bindingConverterContext.getAppClassLoader(),
                        bindingConverterContext.getAppName());
                    methodInfo.setCallbackHandler(methodCallbackHandler);
                }
            }
        }
    }

}