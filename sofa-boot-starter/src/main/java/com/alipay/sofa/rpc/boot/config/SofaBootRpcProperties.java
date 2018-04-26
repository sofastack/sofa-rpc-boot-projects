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

import com.google.common.base.CaseFormat;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * @author khotyn
 */
@ConfigurationProperties(SofaBootRpcProperties.PREFIX)
public class SofaBootRpcProperties {
    static final String PREFIX = "com.alipay.sofa.rpc";

    private Environment environment;
    /* fault-tolerance */
    private String      aftRegulationEffective;
    private String      aftDegradeEffective;
    private String      aftTimeWindow;
    private String      aftLeastWindowCount;
    private String      aftLeastWindowExceptionRateMultiple;
    private String      aftWeightDegradeRate;
    private String      aftWeightRecoverRate;
    private String      aftDegradeLeastWeight;
    private String      aftDegradeMaxIpCount;
    /* Bolt */
    private String      boltPort;
    private String      boltIoThreadCount;
    private String      boltExecutorThreadCount;
    private String      boltAcceptsCount;
    /* rest */
    private String      restHostname;
    private String      restPort;
    private String      restIoThreadCount;
    private String      restExecutorThreadCount;
    private String      restMaxRequestSize;
    private String      restTelnet;
    private String      restDaemon;
    /* dubbo */
    private String      dubboPort;
    private String      dubboIoThreadCount;
    private String      dubboExecutorThreadCount;
    private String      dubboAcceptsCount;
    /* registry */
    private String      registryAddress;

    public SofaBootRpcProperties(Environment environment) {
        this.environment = environment;
    }

    public String getAftRegulationEffective() {
        return StringUtils.isEmpty(aftRegulationEffective) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftRegulationEffective;
    }

    public void setAftRegulationEffective(String aftRegulationEffective) {
        this.aftRegulationEffective = aftRegulationEffective;
    }

    public String getAftDegradeEffective() {
        return StringUtils.isEmpty(aftDegradeEffective) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftDegradeEffective;
    }

    public void setAftDegradeEffective(String aftDegradeEffective) {
        this.aftDegradeEffective = aftDegradeEffective;
    }

    public String getAftTimeWindow() {
        return StringUtils.isEmpty(aftTimeWindow) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftTimeWindow;
    }

    public void setAftTimeWindow(String aftTimeWindow) {
        this.aftTimeWindow = aftTimeWindow;
    }

    public String getAftLeastWindowCount() {
        return StringUtils.isEmpty(aftLeastWindowCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftLeastWindowCount;
    }

    public void setAftLeastWindowCount(String aftLeastWindowCount) {
        this.aftLeastWindowCount = aftLeastWindowCount;
    }

    public String getAftLeastWindowExceptionRateMultiple() {
        return StringUtils.isEmpty(aftLeastWindowExceptionRateMultiple) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftLeastWindowExceptionRateMultiple;
    }

    public void setAftLeastWindowExceptionRateMultiple(String aftLeastWindowExceptionRateMultiple) {
        this.aftLeastWindowExceptionRateMultiple = aftLeastWindowExceptionRateMultiple;
    }

    public String getAftWeightDegradeRate() {
        return StringUtils.isEmpty(aftWeightDegradeRate) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftWeightDegradeRate;
    }

    public void setAftWeightDegradeRate(String aftWeightDegradeRate) {
        this.aftWeightDegradeRate = aftWeightDegradeRate;
    }

    public String getAftWeightRecoverRate() {
        return StringUtils.isEmpty(aftWeightRecoverRate) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftWeightRecoverRate;
    }

    public void setAftWeightRecoverRate(String aftWeightRecoverRate) {
        this.aftWeightRecoverRate = aftWeightRecoverRate;
    }

    public String getAftDegradeLeastWeight() {
        return StringUtils.isEmpty(aftDegradeLeastWeight) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftDegradeLeastWeight;
    }

    public void setAftDegradeLeastWeight(String aftDegradeLeastWeight) {
        this.aftDegradeLeastWeight = aftDegradeLeastWeight;
    }

    public String getAftDegradeMaxIpCount() {
        return StringUtils.isEmpty(aftDegradeMaxIpCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : aftDegradeMaxIpCount;
    }

    public void setAftDegradeMaxIpCount(String aftDegradeMaxIpCount) {
        this.aftDegradeMaxIpCount = aftDegradeMaxIpCount;
    }

    public String getBoltPort() {
        return StringUtils.isEmpty(boltPort) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltPort;
    }

    public void setBoltPort(String boltPort) {
        this.boltPort = boltPort;
    }

    public String getBoltIoThreadCount() {
        return StringUtils.isEmpty(boltIoThreadCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltIoThreadCount;
    }

    public void setBoltIoThreadCount(String boltIoThreadCount) {
        this.boltIoThreadCount = boltIoThreadCount;
    }

    public String getBoltExecutorThreadCount() {
        return StringUtils.isEmpty(boltExecutorThreadCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltExecutorThreadCount;
    }

    public void setBoltExecutorThreadCount(String boltExecutorThreadCount) {
        this.boltExecutorThreadCount = boltExecutorThreadCount;
    }

    public String getBoltAcceptsCount() {
        return StringUtils.isEmpty(boltAcceptsCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltAcceptsCount;
    }

    public void setBoltAcceptsCount(String boltAcceptsCount) {
        this.boltAcceptsCount = boltAcceptsCount;
    }

    public String getRestHostname() {
        return StringUtils.isEmpty(restHostname) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restHostname;
    }

    public void setRestHostname(String restHostname) {
        this.restHostname = restHostname;
    }

    public String getRestPort() {
        return StringUtils.isEmpty(restPort) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restPort;
    }

    public void setRestPort(String restPort) {
        this.restPort = restPort;
    }

    public String getRestIoThreadCount() {
        return StringUtils.isEmpty(restIoThreadCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restIoThreadCount;
    }

    public void setRestIoThreadCount(String restIoThreadCount) {
        this.restIoThreadCount = restIoThreadCount;
    }

    public String getRestExecutorThreadCount() {
        return StringUtils.isEmpty(restExecutorThreadCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restExecutorThreadCount;
    }

    public void setRestExecutorThreadCount(String restExecutorThreadCount) {
        this.restExecutorThreadCount = restExecutorThreadCount;
    }

    public String getRestMaxRequestSize() {
        return StringUtils.isEmpty(restMaxRequestSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restMaxRequestSize;
    }

    public void setRestMaxRequestSize(String restMaxRequestSize) {
        this.restMaxRequestSize = restMaxRequestSize;
    }

    public String getRestTelnet() {
        return StringUtils.isEmpty(restTelnet) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restTelnet;
    }

    public void setRestTelnet(String restTelnet) {
        this.restTelnet = restTelnet;
    }

    public String getRestDaemon() {
        return StringUtils.isEmpty(restDaemon) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restDaemon;
    }

    public void setRestDaemon(String restDaemon) {
        this.restDaemon = restDaemon;
    }

    public String getDubboPort() {
        return StringUtils.isEmpty(dubboPort) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboPort;
    }

    public void setDubboPort(String dubboPort) {
        this.dubboPort = dubboPort;
    }

    public String getDubboIoThreadCount() {
        return StringUtils.isEmpty(dubboIoThreadCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboIoThreadCount;
    }

    public void setDubboIoThreadCount(String dubboIoThreadCount) {
        this.dubboIoThreadCount = dubboIoThreadCount;
    }

    public String getDubboExecutorThreadCount() {
        return StringUtils.isEmpty(dubboExecutorThreadCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboExecutorThreadCount;
    }

    public void setDubboExecutorThreadCount(String dubboExecutorThreadCount) {
        this.dubboExecutorThreadCount = dubboExecutorThreadCount;
    }

    public String getDubboAcceptsCount() {
        return StringUtils.isEmpty(dubboAcceptsCount) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboAcceptsCount;
    }

    public void setDubboAcceptsCount(String dubboAcceptsCount) {
        this.dubboAcceptsCount = dubboAcceptsCount;
    }

    public String getRegistryAddress() {
        return StringUtils.isEmpty(registryAddress) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    private String getDotString(String enclosingMethodName) {
        if (environment == null) {
            return null;
        }
        return environment.getProperty(PREFIX + "." + camelToDot(enclosingMethodName.substring(3)));
    }

    String camelToDot(String camelCaseString) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, camelCaseString).replaceAll("-", ".");
    }

}
