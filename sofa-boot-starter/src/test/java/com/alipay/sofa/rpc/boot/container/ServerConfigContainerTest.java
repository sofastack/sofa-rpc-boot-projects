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
import com.alipay.sofa.rpc.boot.config.SofaBootRpcProperties;
import com.alipay.sofa.rpc.config.ServerConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class ServerConfigContainerTest {
    private SofaBootRpcProperties sofaBootRpcProperties;
    private ServerConfigContainer serverConfigContainer;

    public ServerConfigContainerTest() {
        sofaBootRpcProperties = new SofaBootRpcProperties(null);
        serverConfigContainer = new ServerConfigContainer(sofaBootRpcProperties);
    }

    @Test
    public void testBoltConfiguration() {
        sofaBootRpcProperties.setBoltPort("9090");
        sofaBootRpcProperties.setBoltIoThreadCount("8080");
        sofaBootRpcProperties.setBoltExecutorThreadCount("7070");
        sofaBootRpcProperties.setBoltAcceptsCount(("6060"));

        ServerConfig serverConfig = serverConfigContainer.createBoltServerConfig();
        Assert.assertEquals(9090, serverConfig.getPort());
        Assert.assertEquals(8080, serverConfig.getIoThreads());
        Assert.assertEquals(7070, serverConfig.getMaxThreads());
        Assert.assertEquals(6060, serverConfig.getAccepts());
    }

    @Test
    public void testBoltServerDefaultPort() {
        sofaBootRpcProperties.setBoltPort("");
        ServerConfig serverConfig = serverConfigContainer.createBoltServerConfig();
        Assert.assertEquals(SofaBootRpcConfigConstants.BOLT_PORT_DEFAULT, serverConfig.getPort());
    }

    @Test
    public void testDubboServerConfiguration() {
        sofaBootRpcProperties.setDubboPort("9696");
        sofaBootRpcProperties.setDubboIoThreadCount("8686");
        sofaBootRpcProperties.setDubboExecutorThreadCount("7676");
        sofaBootRpcProperties.setDubboAcceptsCount("6666");

        ServerConfig serverConfig = serverConfigContainer
            .createDubboServerConfig();

        Assert.assertEquals(9696, serverConfig.getPort());
        Assert.assertEquals(8686, serverConfig.getIoThreads());
        Assert.assertEquals(7676, serverConfig.getMaxThreads());
        Assert.assertEquals(6666, serverConfig.getAccepts());
    }

    @Test
    public void testRestServerConfiguration() {
        sofaBootRpcProperties.setRestHostname("host_name");
        sofaBootRpcProperties.setRestPort("123");
        sofaBootRpcProperties.setRestIoThreadCount("456");
        sofaBootRpcProperties.setRestExecutorThreadCount("789");
        sofaBootRpcProperties.setRestMaxRequestSize("1000");
        sofaBootRpcProperties.setRestTelnet("true");
        sofaBootRpcProperties.setRestDaemon("true");

        ServerConfig serverConfig = serverConfigContainer
            .createRestServerConfig();

        Assert.assertEquals("host_name", serverConfig.getBoundHost());
        Assert.assertEquals(123, serverConfig.getPort());
        Assert.assertEquals(456, serverConfig.getIoThreads());
        Assert.assertEquals(789, serverConfig.getMaxThreads());
        Assert.assertEquals(1000, serverConfig.getPayload());
        Assert.assertTrue(serverConfig.isTelnet());
        Assert.assertTrue(serverConfig.isDaemon());
    }
}