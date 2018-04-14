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

import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.runtime.spi.binding.Binding;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 *
 * ConsumerConfig 持有者.维护编程界面级别的RPC组件。
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class ConsumerConfigContainer {

    /**
     * ConsumerConfig 缓存
     */
    private static final ConcurrentMap<Binding, ConsumerConfig> CONSUMER_CONFIG_MAP = new ConcurrentHashMap<Binding, ConsumerConfig>();

    /**
     * 增加 ConsumerConfig
     * @param binding the {@link Binding}
     * @param consumerConfig
     */
    public static void addConsumerConfig(Binding binding, ConsumerConfig consumerConfig) {
        if (binding != null) {
            CONSUMER_CONFIG_MAP.put(binding, consumerConfig);
        }
    }

    /**
     * 移除对应的 ConsumerConfig，并进行unRefer。
     * @param binding the {@link Binding}
     */
    public static void removeAndUnReferConsumerConfig(Binding binding) {
        if (binding != null) {
            ConsumerConfig consumerConfig = CONSUMER_CONFIG_MAP.remove(binding);
            if (consumerConfig != null) {
                consumerConfig.unRefer();
            }
        }
    }

    /**
     * 是否包含 binding
     * @param binding the {@link Binding}
     * @return 是否存在
     */
    public static boolean contains(Binding binding) {
        return CONSUMER_CONFIG_MAP.containsKey(binding);
    }

    /**
     * 获取 ConsumerConfig
     * @param binding the {@link Binding}
     * @return the ConsumerConfig
     */
    public static ConsumerConfig getConsumerConfig(Binding binding) {
        return CONSUMER_CONFIG_MAP.get(binding);
    }

}