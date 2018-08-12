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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import com.google.common.base.CaseFormat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author khotyn
 */
@ConfigurationProperties(SofaBootRpcProperties.PREFIX)
public class SofaBootRpcProperties {
    static final String         PREFIX     = "com.alipay.sofa.rpc";

    private Environment         environment;

    /* fault-tolerance start */
    private String              aftRegulationEffective;
    private String              aftDegradeEffective;
    private String              aftTimeWindow;
    private String              aftLeastWindowCount;
    private String              aftLeastWindowExceptionRateMultiple;
    private String              aftWeightDegradeRate;
    private String              aftWeightRecoverRate;
    private String              aftDegradeLeastWeight;
    private String              aftDegradeMaxIpCount;
    /* fault-tolerance end */

    /* Bolt start*/
    private String              boltPort;
    private String              boltThreadPoolCoreSize;
    private String              boltThreadPoolMaxSize;
    private String              boltThreadPoolQueueSize;
    private String              boltAcceptsSize;
    /* Bolt end*/

    /* H2c start*/
    private String              h2cPort;
    private String              h2cThreadPoolCoreSize;
    private String              h2cThreadPoolMaxSize;
    private String              h2cThreadPoolQueueSize;
    private String              h2cAcceptsSize;
    /* Bolt end*/

    /* rest start*/
    private String              restHostname;
    private String              restPort;
    private String              restIoThreadSize;
    private String              restContextPath;
    // has no use
    private String              restThreadPoolCoreSize;
    private String              restThreadPoolMaxSize;
    private String              restMaxRequestSize;
    private String              restTelnet;
    private String              restDaemon;
    /* rest end */

    /* dubbo  start*/
    private String              dubboPort;
    private String              dubboIoThreadSize;
    //has no use
    private String              dubboThreadPoolCoreSize;
    private String              dubboThreadPoolMaxSize;
    //has no use
    private String              dubboThreadPoolQueueSize;
    private String              dubboAcceptsSize;
    /* dubbo  end*/

    /* registry */
    private String              registryAddress;

    //publish to registry
    private String              virtualHost;

    //publish to registry
    private String              virtualPort;

    //publish to registry virtual host range
    private String              enabledIpRange;

    private String              bindNetworkInterface;

    // bound server
    private String              boundHost;

    // disable lookout
    private String              lookoutCollectDisable;

    //custom registry
    private Map<String, String> registries = new HashMap<String, String>();

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
        return StringUtils.isEmpty(aftLeastWindowExceptionRateMultiple)
            ? getDotString(new Object() {
            }.getClass().getEnclosingMethod().getName())
            : aftLeastWindowExceptionRateMultiple;
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

    public String getDubboIoThreadSize() {
        return StringUtils.isEmpty(dubboIoThreadSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboIoThreadSize;
    }

    public void setDubboIoThreadSize(String dubboIoThreadSize) {
        this.dubboIoThreadSize = dubboIoThreadSize;
    }

    public String getBoltThreadPoolCoreSize() {
        return StringUtils.isEmpty(boltThreadPoolCoreSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltThreadPoolCoreSize;
    }

    public void setBoltThreadPoolCoreSize(String boltThreadPoolCoreSize) {
        this.boltThreadPoolCoreSize = boltThreadPoolCoreSize;
    }

    public String getBoltThreadPoolMaxSize() {
        return StringUtils.isEmpty(boltThreadPoolMaxSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltThreadPoolMaxSize;
    }

    public void setBoltThreadPoolMaxSize(String boltThreadPoolMaxSize) {
        this.boltThreadPoolMaxSize = boltThreadPoolMaxSize;
    }

    public String getBoltAcceptsSize() {
        return StringUtils.isEmpty(boltAcceptsSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltAcceptsSize;
    }

    public void setBoltAcceptsSize(String boltAcceptsSize) {
        this.boltAcceptsSize = boltAcceptsSize;
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

    public String getRestIoThreadSize() {
        return StringUtils.isEmpty(restIoThreadSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restIoThreadSize;
    }

    public void setRestIoThreadSize(String restIoThreadSize) {
        this.restIoThreadSize = restIoThreadSize;
    }

    public String getRestContextPath() {
        return StringUtils.isEmpty(restContextPath) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restContextPath;
    }

    public void setRestContextPath(String restContextPath) {
        this.restContextPath = restContextPath;
    }

    public String getRestThreadPoolMaxSize() {
        return StringUtils.isEmpty(restThreadPoolMaxSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restThreadPoolMaxSize;
    }

    public void setRestThreadPoolMaxSize(String restThreadPoolMaxSize) {
        this.restThreadPoolMaxSize = restThreadPoolMaxSize;
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

    public String getDubboThreadPoolMaxSize() {
        return StringUtils.isEmpty(dubboThreadPoolMaxSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboThreadPoolMaxSize;
    }

    public void setDubboThreadPoolMaxSize(String dubboThreadPoolMaxSize) {
        this.dubboThreadPoolMaxSize = dubboThreadPoolMaxSize;
    }

    public String getDubboAcceptsSize() {
        return StringUtils.isEmpty(dubboAcceptsSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboAcceptsSize;
    }

    public void setDubboAcceptsSize(String dubboAcceptsSize) {
        this.dubboAcceptsSize = dubboAcceptsSize;
    }

    public String getRegistryAddress() {
        return StringUtils.isEmpty(registryAddress) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getBoltThreadPoolQueueSize() {
        return StringUtils.isEmpty(boltThreadPoolQueueSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boltThreadPoolQueueSize;
    }

    public void setBoltThreadPoolQueueSize(String boltThreadPoolQueueSize) {
        this.boltThreadPoolQueueSize = boltThreadPoolQueueSize;
    }

    public String getDubboThreadPoolCoreSize() {
        return StringUtils.isEmpty(dubboThreadPoolCoreSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboThreadPoolCoreSize;
    }

    public void setDubboThreadPoolCoreSize(String dubboThreadPoolCoreSize) {
        this.dubboThreadPoolCoreSize = dubboThreadPoolCoreSize;
    }

    public String getDubboThreadPoolQueueSize() {
        return StringUtils.isEmpty(dubboThreadPoolQueueSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : dubboThreadPoolQueueSize;
    }

    public void setDubboThreadPoolQueueSize(String dubboThreadPoolQueueSize) {
        this.dubboThreadPoolQueueSize = dubboThreadPoolQueueSize;
    }

    public String getRestThreadPoolCoreSize() {
        return StringUtils.isEmpty(restThreadPoolCoreSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : restThreadPoolCoreSize;
    }

    public void setRestThreadPoolCoreSize(String restThreadPoolCoreSize) {
        this.restThreadPoolCoreSize = restThreadPoolCoreSize;
    }

    public String getVirtualHost() {
        return StringUtils.isEmpty(virtualHost) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getBoundHost() {
        return StringUtils.isEmpty(boundHost) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : boundHost;
    }

    public void setBoundHost(String boundHost) {
        this.boundHost = boundHost;
    }

    public String getVirtualPort() {
        return StringUtils.isEmpty(virtualPort) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : virtualPort;
    }

    public void setVirtualPort(String virtualPort) {
        this.virtualPort = virtualPort;
    }

    public String getEnabledIpRange() {
        return StringUtils.isEmpty(enabledIpRange) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : enabledIpRange;
    }

    public void setEnabledIpRange(String enabledIpRange) {
        this.enabledIpRange = enabledIpRange;
    }

    public String getBindNetworkInterface() {
        return StringUtils.isEmpty(bindNetworkInterface) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : bindNetworkInterface;
    }

    public void setBindNetworkInterface(String bindNetworkInterface) {
        this.bindNetworkInterface = bindNetworkInterface;
    }

    public String getH2cPort() {
        return StringUtils.isEmpty(h2cPort) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : h2cPort;
    }

    public void setH2cPort(String h2cPort) {
        this.h2cPort = h2cPort;
    }

    public String getH2cThreadPoolCoreSize() {
        return StringUtils.isEmpty(h2cThreadPoolCoreSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : h2cThreadPoolCoreSize;
    }

    public void setH2cThreadPoolCoreSize(String h2cThreadPoolCoreSize) {
        this.h2cThreadPoolCoreSize = h2cThreadPoolCoreSize;
    }

    public String getH2cThreadPoolMaxSize() {
        return StringUtils.isEmpty(h2cThreadPoolMaxSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : h2cThreadPoolMaxSize;
    }

    public void setH2cThreadPoolMaxSize(String h2cThreadPoolMaxSize) {
        this.h2cThreadPoolMaxSize = h2cThreadPoolMaxSize;
    }

    public String getH2cThreadPoolQueueSize() {
        return StringUtils.isEmpty(h2cThreadPoolQueueSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : h2cThreadPoolQueueSize;
    }

    public void setH2cThreadPoolQueueSize(String h2cThreadPoolQueueSize) {
        this.h2cThreadPoolQueueSize = h2cThreadPoolQueueSize;
    }

    public String getH2cAcceptsSize() {
        return StringUtils.isEmpty(h2cAcceptsSize) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : h2cAcceptsSize;
    }

    public void setH2cAcceptsSize(String h2cAcceptsSize) {
        this.h2cAcceptsSize = h2cAcceptsSize;
    }

    public String getLookoutCollectDisable() {
        return StringUtils.isEmpty(lookoutCollectDisable) ? getDotString(new Object() {
        }.getClass().getEnclosingMethod().getName()) : lookoutCollectDisable;
    }

    public void setLookoutCollectDisable(String lookoutCollectDisable) {
        this.lookoutCollectDisable = lookoutCollectDisable;
    }

    public Map<String, String> getRegistries() {
        return registries;
    }

    public void setRegistries(Map<String, String> registries) {
        this.registries = registries;
    }

    private String getDotString(String enclosingMethodName) {
        if (environment == null) {
            return null;
        }
        return environment.getProperty(PREFIX + "." + camelToDot(enclosingMethodName.substring(3)));
    }

    String camelToDot(String camelCaseString) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, camelCaseString).replaceAll("-",
            ".");
    }
}
