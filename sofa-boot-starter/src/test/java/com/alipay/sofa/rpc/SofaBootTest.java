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
package com.alipay.sofa.rpc;

import com.alipay.hessian.generic.model.GenericObject;
import com.alipay.sofa.rpc.api.GenericService;
import com.alipay.sofa.rpc.bean.CountService;
import com.alipay.sofa.rpc.bean.SampleFacade;
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
@SpringBootTest(classes = SofaBootRpcTestApplication.class)
public class SofaBootTest {

    @Autowired
    private SampleFacade   sampleFacadeReferenceBolt;

    @Autowired
    private SampleFacade   sampleFacadeReferenceRest;

    @Autowired
    private CountService   countServiceReference;

    @Autowired
    private GenericService bookServiceGenericReference;

    @Test
    public void testRemote() {

        System.out.println(sampleFacadeReferenceBolt.sayHi("bolt"));
        System.out.println(sampleFacadeReferenceRest.sayHi("rest"));

        Assert.assertEquals("hi bolt!", sampleFacadeReferenceBolt.sayHi("bolt"));
        Assert.assertEquals("hi rest!", sampleFacadeReferenceRest.sayHi("rest"));
    }

    @Test
    public void testThread() {
        Assert.assertTrue(countServiceReference.getCount().startsWith("1000countServiceThreadPool_name"));
    }

    @Test
    public void testGeneric() {
        GenericObject genericObject = new GenericObject("com.alipay.sofa.rpc.bean.model.BookMarkModel");
        genericObject.putField("name", "Bible");

        GenericObject result = (GenericObject) bookServiceGenericReference.$genericInvoke("getBook",
            new String[] { "com.alipay.sofa.rpc.bean.model.BookMarkModel" }, new Object[] { genericObject });
        Assert.assertEquals("com.alipay.sofa.rpc.bean.model.BookModel", result.getType());
        Assert.assertEquals("Bible", result.getField("name"));
        Assert.assertEquals("lw", result.getField("author"));

    }

}