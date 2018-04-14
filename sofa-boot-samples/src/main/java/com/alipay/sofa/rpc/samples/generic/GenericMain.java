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
package com.alipay.sofa.rpc.samples.generic.bean;

import com.alipay.hessian.generic.model.GenericObject;
import com.alipay.sofa.rpc.api.GenericService;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author <a href="mailto:lw111072@antfin.com">LiWei</a>
 */
public class GenericMain {

    public void start(ApplicationContext applicationContext) {
        GenericService sampleGenericServiceReference = (GenericService) applicationContext
            .getBean("sampleGenericServiceReference");

        GenericObject genericObject = new GenericObject(
            "com.alipay.sofa.rpc.samples.generic.bean.model.SampleGenericParamModel");
        genericObject.putField("name", "Bible");

        GenericObject result = (GenericObject) sampleGenericServiceReference.$genericInvoke("sayGeneric",
            new String[] { "com.alipay.sofa.rpc.samples.generic.bean.model.SampleGenericParamModel" },
            new Object[] { genericObject });

        System.out.println(result.getType());
        System.out.println(result.getField("name"));
        System.out.println(result.getField("value"));
    }
}