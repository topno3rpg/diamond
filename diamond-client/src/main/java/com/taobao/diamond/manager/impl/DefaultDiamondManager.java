/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 * Authors:
 *   leiwen <chrisredfield1985@126.com> , boyan <killme2008@gmail.com>
 */
package com.taobao.diamond.manager.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.diamond.client.DiamondConfigure;
import com.taobao.diamond.client.DiamondSubscriber;
import com.taobao.diamond.client.impl.DefaultSubscriberListener;
import com.taobao.diamond.client.impl.DiamondClientFactory;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;


/**
 * 需要注意的是：一个JVM中一个DataID只能对应一个DiamondManager
 * 
 * @author aoqiong
 * 
 */
public class DefaultDiamondManager implements DiamondManager {

    private static final Log log = LogFactory.getLog(DefaultDiamondManager.class);

    private DiamondSubscriber diamondSubscriber = null;
    private final List<ManagerListener> managerListeners = new LinkedList<ManagerListener>();
    private final String dataId;
    private final String group;


    public DefaultDiamondManager(String dataId, ManagerListener managerListener) {
        this(null, dataId, managerListener);
    }


    public DefaultDiamondManager(String group, String dataId, ManagerListener managerListener) {
        this.dataId = dataId;
        this.group = group;

        diamondSubscriber = DiamondClientFactory.getSingletonDiamondSubscriber();

        this.managerListeners.add(managerListener);
        ((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).addManagerListeners(this.dataId,
            this.group, this.managerListeners);
        diamondSubscriber.addDataId(this.dataId, this.group);
        diamondSubscriber.start();

    }


    public DefaultDiamondManager(String dataId, List<ManagerListener> managerListenerList) {
        this(null, dataId, managerListenerList);
    }


    /**
     * 使用指定的集群类型clusterType
     * 
     * @param group
     * @param dataId
     * @param managerListenerList
     * @param clusterType
     */
    public DefaultDiamondManager(String group, String dataId, List<ManagerListener> managerListenerList) {
        this.dataId = dataId;
        this.group = group;

        diamondSubscriber = DiamondClientFactory.getSingletonDiamondSubscriber();

        this.managerListeners.addAll(managerListenerList);
        ((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).addManagerListeners(this.dataId,
            this.group, this.managerListeners);
        diamondSubscriber.addDataId(this.dataId, this.group);
        diamondSubscriber.start();
    }


    public void setManagerListener(ManagerListener managerListener) {
        this.managerListeners.clear();
        this.managerListeners.add(managerListener);

        ((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).removeManagerListeners(this.dataId,
            this.group);
        ((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).addManagerListeners(this.dataId,
            this.group, this.managerListeners);
    }


    public void close() {
        /**
         * 因为同一个DataID只能对应一个MnanagerListener，所以，关闭时一次性关闭所有ManagerListener即可
         */
        ((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).removeManagerListeners(this.dataId,
            this.group);

        diamondSubscriber.removeDataId(dataId, group);
        if (diamondSubscriber.getDataIds().size() == 0) {
            diamondSubscriber.close();
        }

    }
    
    long defaultTimeoutIfInvalid(long timeout) {
    	if (timeout > 0) {
    		return timeout;
    	}
    	return diamondSubscriber.getDiamondConfigure().getOnceTimeout();
    }

    public String getConfigureInfomation(long timeout) {
        return diamondSubscriber.getConfigureInfomation(this.dataId, this.group, defaultTimeoutIfInvalid(timeout));
    }

    public String getConfigureInfomation() {
    	return getConfigureInfomation(0);
    }

    public String getAvailableConfigureInfomation(long timeout) {
        return diamondSubscriber.getAvailableConfigureInfomation(dataId, group, defaultTimeoutIfInvalid(timeout));
    }
    
    public String getAvailableConfigureInfomation() {
    	return getAvailableConfigureInfomation(0);
    }

    public String getAvailableConfigureInfomationFromSnapshot(long timeout) {
        return diamondSubscriber.getAvailableConfigureInfomationFromSnapshot(dataId, group, defaultTimeoutIfInvalid(timeout));
    }

    public String getAvailableConfigureInfomationFromSnapshot() {
    	return getAvailableConfigureInfomationFromSnapshot(0);
    }

    public Properties getAvailablePropertiesConfigureInfomation(long timeout) {
        String configInfo = this.getAvailableConfigureInfomation(timeout);
        Properties properties = new Properties();
        
        if (StringUtils.isBlank(configInfo)) {
        	return properties;
        }
        
        try {
            properties.load(new StringReader(configInfo));
            return properties;
        }
        catch (IOException e) {
            log.warn("装载properties失败：" + configInfo, e);
            throw new RuntimeException("装载properties失败：" + configInfo, e);
        }
    }

    public Properties getAvailablePropertiesConfigureInfomation() {
    	return getAvailablePropertiesConfigureInfomation(0);
    }

    public Properties getAvailablePropertiesConfigureInfomationFromSnapshot(long timeout) {
        String configInfo = this.getAvailableConfigureInfomationFromSnapshot(timeout);
        Properties properties = new Properties();
        
        if (StringUtils.isBlank(configInfo)) {
        	return properties;
        }
        
        try {
            properties.load(new StringReader(configInfo));
            return properties;
        }
        catch (IOException e) {
            log.warn("装载properties失败：" + configInfo, e);
            throw new RuntimeException("装载properties失败：" + configInfo, e);
        }
    }

    public Properties getAvailablePropertiesConfigureInfomationFromSnapshot() {
    	return getAvailablePropertiesConfigureInfomationFromSnapshot(0);
    }

    public void setManagerListeners(List<ManagerListener> managerListenerList) {
    	clearManagerListeners();
    	
        this.managerListeners.addAll(managerListenerList);
        ((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).addManagerListeners(this.dataId,
            this.group, this.managerListeners);
    }

    public void clearManagerListeners() {
    	this.managerListeners.clear();
    	((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).removeManagerListeners(this.dataId,
                this.group);
    }
    
    public void addManagerListener(ManagerListener managerListener) {
    	this.managerListeners.add(managerListener);
    	((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).addManagerListener(this.dataId,
                this.group, managerListener);
    }
    
    public void addManagerListeners(List<ManagerListener> managerListenerList) {
    	this.managerListeners.addAll(managerListenerList);
    	((DefaultSubscriberListener) diamondSubscriber.getSubscriberListener()).addManagerListeners(this.dataId,
                this.group, managerListenerList);
    }

    public DiamondConfigure getDiamondConfigure() {
        return diamondSubscriber.getDiamondConfigure();
    }


    public void setDiamondConfigure(DiamondConfigure diamondConfigure) {
        diamondSubscriber.setDiamondConfigure(diamondConfigure);
    }


    public Properties getPropertiesConfigureInfomation(long timeout) {
        String configInfo = this.getConfigureInfomation(timeout);
        Properties properties = new Properties();
        
        if (StringUtils.isBlank(configInfo)) {
        	return properties;
        }
        
        try {
            properties.load(new StringReader(configInfo));
            return properties;
        }
        catch (IOException e) {
            log.warn("装载properties失败：" + configInfo, e);
            throw new RuntimeException("装载properties失败：" + configInfo, e);
        }
    }

    public Properties getPropertiesConfigureInfomation() {
    	return getPropertiesConfigureInfomation(0);
    }

    public List<ManagerListener> getManagerListeners() {
        return Collections.unmodifiableList(this.managerListeners);
    }

}
