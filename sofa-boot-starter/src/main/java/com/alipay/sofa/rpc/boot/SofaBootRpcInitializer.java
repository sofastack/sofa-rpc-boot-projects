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
package com.alipay.sofa.rpc.boot;

import com.alipay.sofa.common.log.Constants;
import com.alipay.sofa.infra.log.space.SofaBootLogSpaceIsolationInit;
import com.alipay.sofa.rpc.boot.common.SofaBootRpcRuntimeException;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcConfigConstants;
import com.alipay.sofa.rpc.boot.container.SpringBridge;
import com.alipay.sofa.rpc.boot.log.SofaBootRpcLoggerFactory;
import com.alipay.sofa.rpc.log.factory.RpcLoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * SOFABoot RPC 初始化
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class SofaBootRpcInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        SpringBridge.setApplicationContext(applicationContext);

        checkAppName(applicationContext.getEnvironment().getProperty(SofaBootRpcConfigConstants.APP_NAME));

        initStarterLog(applicationContext.getEnvironment());

        initRpcCoreLog(applicationContext.getEnvironment());

    }

    private void checkAppName(String appName) {
        if (!StringUtils.hasText(appName)) {
            throw new SofaBootRpcRuntimeException("Please add '" + SofaBootRpcConfigConstants.APP_NAME +
                "' in application.properties");
        }
    }

    /**
     * init sofa rpc starter log config
     *
     * @param environment
     */
    private void initStarterLog(Environment environment) {
        //log level
        String logLevelKey = Constants.LOG_LEVEL_PREFIX + SofaBootRpcLoggerFactory.RPC_LOG_SPACE;
        SofaBootLogSpaceIsolationInit.initSofaBootLogger(environment, logLevelKey);
    }

    /**
     * init sofa rpc log config
     *
     * @param environment
     */
    private void initRpcCoreLog(Environment environment) {
        //log level
        String logLevelKey = Constants.LOG_LEVEL_PREFIX + RpcLoggerFactory.RPC_LOG_SPACE;
        SofaBootLogSpaceIsolationInit.initSofaBootLogger(environment, logLevelKey);
    }
}
