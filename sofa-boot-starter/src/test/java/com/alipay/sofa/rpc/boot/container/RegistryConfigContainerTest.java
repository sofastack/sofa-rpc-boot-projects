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

import com.alipay.sofa.rpc.boot.common.SofaBootRpcRuntimeException;
import com.alipay.sofa.rpc.boot.config.LocalFileConfigurator;
import com.alipay.sofa.rpc.boot.config.SofaBootRpcProperties;
import com.alipay.sofa.rpc.boot.config.ZookeeperConfigurator;
import com.alipay.sofa.rpc.config.RegistryConfig;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class RegistryConfigContainerTest {
    @Rule
    public ExpectedException        thrown = ExpectedException.none();

    private SofaBootRpcProperties   sofaBootRpcProperties;
    private RegistryConfigContainer registryConfigContainer;

    public RegistryConfigContainerTest() {
        sofaBootRpcProperties = new SofaBootRpcProperties(null);
        registryConfigContainer = new RegistryConfigContainer(sofaBootRpcProperties,
            new ZookeeperConfigurator(sofaBootRpcProperties),
            new LocalFileConfigurator(sofaBootRpcProperties));
    }

    @Test
    public void testGetLocalRegistryConfig() {
        sofaBootRpcProperties.setRegistryAddress("local:/home/admin/local");
        RegistryConfig registryConfigLocal = registryConfigContainer.createLocalRegistryConfig();
        Assert.assertEquals("local", registryConfigLocal.getProtocol());
        Assert.assertEquals("/home/admin/local", registryConfigLocal.getFile());
    }

    @Test
    public void testZooKeeperRegistryConfig() {
        sofaBootRpcProperties.setRegistryAddress("zookeeper://127.0.0.1:2181?file=/home/admin/zookeeper");
        RegistryConfig registryConfigZk = registryConfigContainer.createZookeeperRegistryConfig();
        Assert.assertEquals("zookeeper", registryConfigZk.getProtocol());
        Assert.assertEquals("/home/admin/zookeeper", registryConfigZk.getFile());
    }

    @Test
    public void testWrongRegistryConfig() {
        thrown.expect(SofaBootRpcRuntimeException.class);
        thrown.expectMessage("protocol[no] is not supported");
        sofaBootRpcProperties.setRegistryAddress("no");
        registryConfigContainer.getRegistryConfig();
    }

}