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

import com.alipay.sofa.rpc.samples.direct.DirectSample;
import com.alipay.sofa.rpc.samples.dubbo.DubboSample;
import com.alipay.sofa.rpc.samples.filter.FilterSample;
import com.alipay.sofa.rpc.samples.generic.GenericSample;
import com.alipay.sofa.rpc.samples.invoke.InvokeSample;
import com.alipay.sofa.rpc.samples.rest.RestSample;
import com.alipay.sofa.rpc.samples.threadpool.ThreadPoolSample;
import com.alipay.sofa.test.runner.SofaBootRunner;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
@RunWith(SofaBootRunner.class)
@SpringBootTest(classes = SofaBootRpcSamplesApplication.class)
public class SofaBootRpcSamplesTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testInvoke() throws InterruptedException {

        Assert.assertEquals("sync", new InvokeSample().start(applicationContext));
    }

    @Test
    public void testGeneric() throws InterruptedException {

        Assert.assertEquals("sample generic value", new GenericSample().start(applicationContext));
    }

    @Test
    public void testFilter() throws InterruptedException {

        Assert.assertEquals("filter", new FilterSample().start(applicationContext));
    }

    @Test
    public void testDirect() throws InterruptedException {

        Assert.assertEquals("direct", new DirectSample().start(applicationContext));
    }

    @Test
    public void testThreadPool() throws InterruptedException {

        Assert.assertTrue(new ThreadPoolSample().start(applicationContext).startsWith(
            "threadPool[customerThreadPool_name"));
    }

    @Test
    public void testRest() throws InterruptedException {

        Assert.assertEquals("rest", new RestSample().start(applicationContext));
    }

    @Test
    public void testDubbo() throws InterruptedException {

        Assert.assertEquals("dubbo", new DubboSample().start(applicationContext));
    }
}