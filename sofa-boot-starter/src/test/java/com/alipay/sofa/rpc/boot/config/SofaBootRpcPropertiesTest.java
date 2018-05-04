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
package com.alipay.sofa.rpc.boot.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootApplication
@SpringBootTest(properties = {
                              SofaBootRpcProperties.PREFIX + ".bolt.port=5000",
                              "com_alipay_sofa_rpc_bolt_thread_pool_max_size=600"
})
public class SofaBootRpcPropertiesTest {
    @Autowired
    private SofaBootRpcProperties sofaBootRpcProperties;

    @Test
    public void testCamelCaseToDot() {
        Assert.assertEquals("com.alipay.sofa", sofaBootRpcProperties.camelToDot("comAlipaySofa"));
        Assert.assertEquals("com.alipay.sofa", sofaBootRpcProperties.camelToDot("ComAlipaySofa"));
    }

    @Test
    public void testDotConfig() {
        Assert.assertEquals("5000", sofaBootRpcProperties.getBoltPort());
    }

    @Test
    public void testUnderscoreConfig() {
        Assert.assertEquals("600", sofaBootRpcProperties.getBoltThreadPoolMaxSize());
    }
}
