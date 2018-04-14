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
package com.alipay.sofa.rpc.samples;

import com.alipay.hessian.generic.model.GenericObject;
import com.alipay.sofa.rpc.api.GenericService;
import com.alipay.sofa.rpc.api.future.SofaResponseFuture;
import com.alipay.sofa.rpc.samples.invoke.HelloFutureService;
import com.alipay.sofa.rpc.samples.invoke.HelloSyncService;
import com.alipay.sofa.rpc.samples.threadpool.ThreadPoolService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SofaBootRpcSamplesApplication.class)
public class SofaBootRpcSamplesTest {

    @Autowired
    private HelloSyncService   helloSyncServiceReference;

    @Autowired
    private HelloFutureService helloFutureServiceReference;

    @Autowired
    private GenericService     sampleGenericServiceReference;

    @Autowired
    private ThreadPoolService  threadPoolServiceReference;

    @Test
    public void testInvoke() throws InterruptedException {
        // invoke sync
        Assert.assertEquals("sync", helloSyncServiceReference.saySync("sync"));

        // invoke future
        helloFutureServiceReference.sayFuture("future");
        Assert.assertEquals("future", SofaResponseFuture.getResponse(5000, true));

    }

    @Test
    public void testGeneric() {

        GenericObject genericObject = new GenericObject(
            "com.alipay.sofa.rpc.samples.generic.SampleGenericParamModel");
        genericObject.putField("name", "Bible");
        GenericObject result = (GenericObject) sampleGenericServiceReference.$genericInvoke("sayGeneric",
            new String[] { "com.alipay.sofa.rpc.samples.generic.SampleGenericParamModel" },
            new Object[] { genericObject });

        Assert
            .assertEquals("com.alipay.sofa.rpc.samples.generic.SampleGenericResultModel", result.getType());
        Assert.assertEquals("Bible", result.getField("name"));
        Assert.assertEquals("sample generic value", result.getField("value"));
    }

    @Test
    public void testThreadPool() {
        Assert.assertTrue(threadPoolServiceReference.sayThreadPool("threadPool").startsWith(
            "threadPool[customerThreadPool_name"));

    }
}