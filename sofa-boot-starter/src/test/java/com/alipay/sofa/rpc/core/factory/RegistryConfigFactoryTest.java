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

import com.alipay.sofa.rpc.common.SofaBootRpcRuntimeException;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.SofaBootRpcConfigConstants;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class RegistryConfigFactoryTest {

    @Test
    public void testGetRegistryConfig() {

        System.setProperty(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL,
            SofaBootRpcConfigConstants.REGISTRY_PROTOCOL_LOCAL);
        System.setProperty(SofaBootRpcConfigConstants.REGISTRY_FILE_PATH, "/home/admin/local");

        RegistryConfig registryConfigLocal = RegistryConfigFactory.createLocalRegistryConfig();
        Assert.assertEquals("local", registryConfigLocal.getProtocol());
        Assert.assertEquals("/home/admin/local", registryConfigLocal.getFile());

        System.setProperty(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL,
            "zookeeper://127.0.0.1:2181?file=/home/admin/zookeeper");

        RegistryConfig registryConfigZk = RegistryConfigFactory.createZookeeperRegistryConfig();
        Assert.assertEquals("zookeeper", registryConfigZk.getProtocol());
        Assert.assertEquals("/home/admin/zookeeper", registryConfigZk.getFile());

        System.setProperty(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL, "no");

        try {
            RegistryConfig registryConfigNo = RegistryConfigFactory.getRegistryConfig();
            Assert.fail("No Exception thrown");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof SofaBootRpcRuntimeException);
            Assert.assertEquals("protocl[no] is not supported", e.getMessage());
        }

        //clear
        System.clearProperty(SofaBootRpcConfigConstants.REGISTRY_PROTOCOL);
        System.clearProperty(SofaBootRpcConfigConstants.REGISTRY_FILE_PATH);
    }

}