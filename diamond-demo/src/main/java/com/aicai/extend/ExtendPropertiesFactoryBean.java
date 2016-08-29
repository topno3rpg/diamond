package com.aicai.extend;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;
import com.taobao.diamond.client.DiamondClients;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.util.CollectionUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * Created by Admin on 2016/8/8.
 */
public class ExtendPropertiesFactoryBean extends PropertiesFactoryBean {
    static Logger log = LoggerFactory.getLogger(ExtendPropertiesFactoryBean.class);

    static {
        String[/*groupId*/][/*dataId*/] configs = new String[][]{{"demo", "global"}, {"demo", "private"}};

        log.info("开始加载配置中心");
        for (String[] config : configs) {
            String groupId = config[0];
            String dataId = config[1];
            try {
                DiamondManager diamondManager = DiamondClients.createSafeDiamondManager(groupId, dataId
                        , new ManagerListener() {
                            public void receiveConfigInfo(String configInfo) {
                                // 配置变更异步通知
                                try {
                                    Properties properties = new Properties();
                                    properties.load(new ByteArrayInputStream(configInfo.getBytes()));
                                    CollectionUtils.mergePropertiesIntoMap(properties, DynamicProperties.staticProperties);
                                } catch (Throwable ignore) {
                                }
                            }

                            public Executor getExecutor() {
                                return null;
                            }
                        }
                );
                String allConfig = diamondManager.getAvailableConfigureInfomation();
                Properties properties = new Properties();
                properties.load(new ByteArrayInputStream(allConfig.getBytes()));
                CollectionUtils.mergePropertiesIntoMap(properties, DynamicProperties.staticProperties);
            } catch (IOException e) {
                throw new RuntimeException("加载配置信息IO异常！groupId=" + groupId + ", dataId = " + dataId, e);
            } catch (Exception e) {
                throw new RuntimeException("加载配置信息异常！groupId=" + groupId + ", dataId = " + dataId, e);
            }
        }
        log.info("成功加载配置中心");
    }

    @Override
    public Properties createProperties() throws IOException {
        this.setProperties(DynamicProperties.staticProperties);
        return this.mergeProperties();
    }

}
