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

import com.alipay.hessian.generic.model.GenericObject;
import com.alipay.sofa.rpc.api.GenericService;
import com.alipay.sofa.rpc.api.future.SofaResponseFuture;
import com.alipay.sofa.rpc.boot.annotation.AnnotationService;
import com.alipay.sofa.rpc.boot.container.ConsumerConfigContainer;
import com.alipay.sofa.rpc.boot.container.SpringBridge;
import com.alipay.sofa.rpc.boot.direct.DirectService;
import com.alipay.sofa.rpc.boot.dubbo.DubboService;
import com.alipay.sofa.rpc.boot.filter.FilterService;
import com.alipay.sofa.rpc.boot.globalfilter.GlobalFilterService;
import com.alipay.sofa.rpc.boot.invoke.CallbackImpl;
import com.alipay.sofa.rpc.boot.invoke.HelloCallbackService;
import com.alipay.sofa.rpc.boot.invoke.HelloFutureService;
import com.alipay.sofa.rpc.boot.invoke.HelloSyncService;
import com.alipay.sofa.rpc.boot.lazy.LazyService;
import com.alipay.sofa.rpc.boot.rest.RestService;
import com.alipay.sofa.rpc.boot.retry.RetriesService;
import com.alipay.sofa.rpc.boot.retry.RetriesServiceImpl;
import com.alipay.sofa.rpc.boot.threadpool.ThreadPoolService;
import com.alipay.sofa.rpc.config.ConsumerConfig;
import com.alipay.sofa.rpc.core.exception.SofaRpcException;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.alipay.sofa.runtime.spi.binding.Binding;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
@SpringBootTest
@RunWith(SpringRunner.class)
@ImportResource("classpath*:spring/test_all.xml")
public class SofaBootRpcAllTest {
    @Rule
    public ExpectedException     thrown = ExpectedException.none();

    @Autowired
    private HelloSyncService     helloSyncService;

    @Autowired
    private HelloFutureService   helloFutureService;

    @Autowired
    private HelloCallbackService helloCallbackService;

    @Autowired
    private FilterService        filterService;

    @Autowired
    private GlobalFilterService  globalFilterService;

    @Autowired
    private DirectService        directService;

    @Autowired
    private GenericService       genericService;

    @Autowired
    private ThreadPoolService    threadPoolService;

    @Autowired
    private RestService          restService;

    @Autowired
    private DubboService         dubboService;

    @Autowired
    private RetriesService       retriesServiceBolt;

    @Autowired
    private RetriesService       retriesServiceDubbo;

    @Autowired
    private LazyService          lazyServiceBolt;

    @Autowired
    private LazyService          lazyServiceDubbo;

    @SofaReference(binding = @SofaReferenceBinding(bindingType = "bolt"),
            jvmFirst = false)
    private AnnotationService    annotationService;

    @SofaReference(binding = @SofaReferenceBinding(bindingType = "bolt", serializeType = "protobuf"),
            jvmFirst = false)
    private AnnotationService    annotationServicePb;

    @SofaReference(binding = @SofaReferenceBinding(bindingType = "bolt", loadBalancer = "roundRobin"), uniqueId = "loadbalancer")
    private AnnotationService    annotationLoadBalancerService;

    @Test
    public void testInvoke() throws InterruptedException {
        Assert.assertEquals("sync", helloSyncService.saySync("sync"));

        helloFutureService.sayFuture("future");
        Assert.assertEquals("future", SofaResponseFuture.getResponse(1000, true));

        helloCallbackService.sayCallback("callback");
        Thread.sleep(1000);
        Assert.assertEquals("callback", CallbackImpl.result);
    }

    @Test
    public void testGlobalFilter() {
        Assert.assertEquals("globalFilter_change", globalFilterService.sayGlobalFilter("globalFilter"));
    }

    @Test
    public void testDirect() throws InterruptedException {

        Thread.sleep(5000);

        Assert.assertEquals("direct", directService.sayDirect("direct"));

    }

    @Test
    public void testFilter() {
        Assert.assertEquals("filter_change", filterService.sayFilter("filter"));
    }

    @Test
    public void testGeneric() {
        GenericObject genericObject = new GenericObject(
            "com.alipay.sofa.rpc.boot.generic.GenericParamModel");
        genericObject.putField("name", "Bible");

        GenericObject result = (GenericObject) genericService.$genericInvoke("sayGeneric",
            new String[] { "com.alipay.sofa.rpc.boot.generic.GenericParamModel" },
            new Object[] { genericObject });

        Assert.assertEquals("com.alipay.sofa.rpc.boot.generic.GenericResultModel", result.getType());
        Assert.assertEquals("Bible", result.getField("name"));
        Assert.assertEquals("sample generic value", result.getField("value"));
    }

    @Test
    public void testThreadPool() {
        Assert.assertTrue(threadPoolService.sayThreadPool("threadPool").startsWith(
            "threadPool[SOFA-customerThreadPool_name"));

    }

    @Test
    public void testRest() {
        Assert.assertEquals("rest", restService.sayRest("rest"));
    }

    @Test
    public void testDubbo() {
        Assert.assertEquals("dubbo", dubboService.sayDubbo("dubbo"));
    }

    @Test
    public void testRetries() throws InterruptedException {
        Assert.assertEquals("retries_bolt", retriesServiceBolt.sayRetry("retries_bolt"));
        Assert.assertEquals("retries_dubbo", retriesServiceDubbo.sayRetry("retries_dubbo"));

        Assert.assertEquals(6, RetriesServiceImpl.count.get());
    }

    @Test
    public void testLazy() {
        Assert.assertEquals("lazy_bolt", lazyServiceBolt.sayLazy("lazy_bolt"));
        Assert.assertEquals("lazy_dubbo", lazyServiceDubbo.sayLazy("lazy_dubbo"));
    }

    @Test
    public void testAnnotation() {
        Assert.assertEquals("Hello, Annotation", annotationService.hello());
    }

    // Encode on serialization should failed
    @Test
    public void testAnnotationProtobuf() {
        thrown.expect(SofaRpcException.class);
        thrown.expectMessage("com.alipay.remoting.exception.SerializationException: 0");
        annotationServicePb.hello();
    }

    @Test
    public void testLoadBalancerAnnotation() throws NoSuchFieldException, IllegalAccessException {
        ConsumerConfigContainer ccc = SpringBridge.getConsumerConfigContainer();
        Field consumerConfigMapField = ConsumerConfigContainer.class.getDeclaredField("consumerConfigMap");
        consumerConfigMapField.setAccessible(true);
        ConcurrentMap<Binding, ConsumerConfig> consumerConfigMap = (ConcurrentMap<Binding, ConsumerConfig>) consumerConfigMapField
            .get(ccc);

        boolean found = false;
        for (ConsumerConfig consumerConfig : consumerConfigMap.values()) {
            if ("loadbalancer".equals(consumerConfig.getUniqueId()) &&
                AnnotationService.class.getName().equals(consumerConfig.getInterfaceId())) {
                found = true;
                Assert.assertEquals("roundRobin", consumerConfig.getLoadBalancer());
            }
        }

        Assert.assertTrue("Found roundrobin reference", found);
    }
}
