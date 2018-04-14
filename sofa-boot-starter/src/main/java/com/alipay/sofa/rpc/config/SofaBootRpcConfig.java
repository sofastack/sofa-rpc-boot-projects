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
package com.alipay.sofa.rpc.config;

import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class SofaBootRpcConfig {

    private static Environment environment = null;

    /**
     * 根据key读取配置value
     * @param key 配置key
     * @return value。如果不存在则返回null。
     */
    public static String getProperty(String key) {

        if (environment != null) {
            return environment.getProperty(key);
        } else {
            return null;
        }
    }

    /**
     * 获取{@link Environment}
     * @return Environment the {@link Environment}
     */
    public static Environment getEnvironment() {
        return environment;
    }

    /**
     * 设置{@link Environment}
     * @param environment the {@link Environment}
     */
    public static void setEnvironment(Environment environment) {
        SofaBootRpcConfig.environment = environment;
    }

    /**
     * 从System和SOFABoot配置文件中根据key读取value。
     * 读取优先级：
     * 1）从System中读取XXX.XXX形式的key
     * 2）从System中读取XXX_XXX形式的key
     * 3）从properties中读取XXX.XXX形式的key
     * 4）从properties中读取XXX_XXX形式的key
     * @param key 配置key
     * @return 配置value
     */
    public static String getPropertyAllCircumstances(String key) {
        if (!StringUtils.hasText(key)) {
            return null;
        }

        String lineKey = key.replaceAll("\\.", "_");

        String value = System.getProperty(key);
        if (!StringUtils.hasText(value)) {
            value = System.getProperty(lineKey);
        }
        if (!StringUtils.hasText(value)) {
            value = SofaBootRpcConfig.getProperty(key);
        }
        if (!StringUtils.hasText(value)) {
            value = SofaBootRpcConfig.getProperty(lineKey);
        }

        return value;
    }

}