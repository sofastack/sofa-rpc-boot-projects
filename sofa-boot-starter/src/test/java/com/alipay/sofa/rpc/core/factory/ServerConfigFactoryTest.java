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

import com.alipay.sofa.rpc.config.ServerConfig;
import com.alipay.sofa.rpc.config.SofaBootRpcConfigConstants;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class ServerConfigFactoryTest {

    @Test
    public void testGetServerConfig() {

        System.setProperty(SofaBootRpcConfigConstants.BOLT_PORT, "9090");
        System.setProperty(SofaBootRpcConfigConstants.BOLT_IO_THREAD_COUNT, "8080");
        System.setProperty(SofaBootRpcConfigConstants.BOLT_EXECUTOR_THREAD_COUNT, "7070");
        System.setProperty(SofaBootRpcConfigConstants.BOLT_ACCEPTS_COUNT, "6060");

        System.setProperty(SofaBootRpcConfigConstants.REST_HOSTNAME, "host_name");
        System.setProperty(SofaBootRpcConfigConstants.REST_PORT, "123");
        System.setProperty(SofaBootRpcConfigConstants.REST_IO_THREAD_COUNT, "456");
        System.setProperty(SofaBootRpcConfigConstants.REST_EXECUTOR_THREAD_COUNT, "789");
        System.setProperty(SofaBootRpcConfigConstants.REST_MAX_REQUEST_SIZE, "1000");
        System.setProperty(SofaBootRpcConfigConstants.REST_TELNET, "true");
        System.setProperty(SofaBootRpcConfigConstants.REST_DAEMON, "true");

        System.setProperty(SofaBootRpcConfigConstants.DUBBO_PORT, "9696");
        System.setProperty(SofaBootRpcConfigConstants.DUBBO_IO_THREAD_COUNT, "8686");
        System.setProperty(SofaBootRpcConfigConstants.DUBBO_EXECUTOR_THREAD_COUNT, "7676");
        System.setProperty(SofaBootRpcConfigConstants.DUBBO_ACCEPTS_COUNT, "6666");

        ServerConfig serverConfigBolt = ServerConfigFactory
            .createBoltServerConfig();
        ServerConfig serverConfigRest = ServerConfigFactory
            .createRestServerConfig();
        ServerConfig serverConfigDubbo = ServerConfigFactory
            .createDubboServerConfig();

        //assert
        Assert.assertEquals(9090, serverConfigBolt.getPort());
        Assert.assertEquals(8080, serverConfigBolt.getIoThreads());
        Assert.assertEquals(7070, serverConfigBolt.getMaxThreads());
        Assert.assertEquals(6060, serverConfigBolt.getAccepts());

        Assert.assertEquals("host_name", serverConfigRest.getBoundHost());
        Assert.assertEquals(123, serverConfigRest.getPort());
        Assert.assertEquals(456, serverConfigRest.getIoThreads());
        Assert.assertEquals(789, serverConfigRest.getMaxThreads());
        Assert.assertEquals(1000, serverConfigRest.getPayload());
        Assert.assertEquals(true, serverConfigRest.isTelnet());
        Assert.assertEquals(true, serverConfigRest.isDaemon());

        Assert.assertEquals(9696, serverConfigDubbo.getPort());
        Assert.assertEquals(8686, serverConfigDubbo.getIoThreads());
        Assert.assertEquals(7676, serverConfigDubbo.getMaxThreads());
        Assert.assertEquals(6666, serverConfigDubbo.getAccepts());

        //clear
        System.clearProperty(SofaBootRpcConfigConstants.BOLT_PORT);
        System.clearProperty(SofaBootRpcConfigConstants.BOLT_IO_THREAD_COUNT);
        System.clearProperty(SofaBootRpcConfigConstants.BOLT_EXECUTOR_THREAD_COUNT);
        System.clearProperty(SofaBootRpcConfigConstants.BOLT_ACCEPTS_COUNT);

        System.clearProperty(SofaBootRpcConfigConstants.REST_HOSTNAME);
        System.clearProperty(SofaBootRpcConfigConstants.REST_PORT);
        System.clearProperty(SofaBootRpcConfigConstants.REST_IO_THREAD_COUNT);
        System.clearProperty(SofaBootRpcConfigConstants.REST_EXECUTOR_THREAD_COUNT);
        System.clearProperty(SofaBootRpcConfigConstants.REST_MAX_REQUEST_SIZE);
        System.clearProperty(SofaBootRpcConfigConstants.REST_TELNET);
        System.clearProperty(SofaBootRpcConfigConstants.REST_DAEMON);

        System.clearProperty(SofaBootRpcConfigConstants.DUBBO_PORT);
        System.clearProperty(SofaBootRpcConfigConstants.DUBBO_IO_THREAD_COUNT);
        System.clearProperty(SofaBootRpcConfigConstants.DUBBO_EXECUTOR_THREAD_COUNT);
        System.clearProperty(SofaBootRpcConfigConstants.DUBBO_ACCEPTS_COUNT);

        ServerConfigFactory.closeAllServer();
    }
}