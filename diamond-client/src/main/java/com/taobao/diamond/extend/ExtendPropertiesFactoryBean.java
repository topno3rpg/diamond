package com.taobao.diamond.extend;

import com.taobao.diamond.client.DiamondClients;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by Guozp on 2016/8/8.
 */
public class ExtendPropertiesFactoryBean extends PropertiesFactoryBean {
    Logger logger = LoggerFactory.getLogger(ExtendPropertiesFactoryBean.class);

    void loadConfig(String[/*groupId*/][/*dataId*/] configs) {
        logger.info("开始加载配置中心");
        for (String[] config : configs) {
            String groupId = config[0];
            final String dataId = config[1];
            try {
                DiamondManager diamondManager = DiamondClients.createSafeDiamondManager(groupId, dataId
                        , new ManagerListener() {
                            public void receiveConfigInfo(String configInfo) {
                                // 配置变更异步通知
                                try {
                                    loadConfigByDataId(dataId, configInfo);
                                } catch (Throwable ignore) {
                                }
                            }

                            public Executor getExecutor() {
                                return null;
                            }
                        }
                );
                String allConfig = diamondManager.getAvailableConfigureInfomation();
                loadConfigByDataId(dataId, allConfig);
            } catch (IOException e) {
                throw new RuntimeException("加载配置信息IO异常！groupId=" + groupId + ", dataId = " + dataId, e);
            } catch (Exception e) {
                throw new RuntimeException("加载配置信息异常！groupId=" + groupId + ", dataId = " + dataId, e);
            }
        }
        logger.info("成功加载配置中心");
    }

    void loadConfigByDataId(String dataId, String config) throws IOException {
        if ("logconfig".equalsIgnoreCase(dataId)) {
            //加载日志配置
            String fileName = "log4j.properties";
            loadLogConfig(fileName, config);
        } else if ("dubboconfig".equalsIgnoreCase(dataId)) {
            //加载日志配置
            String fileName = "dubbo.properties";
            loadLogConfig(fileName, config);
        } else {
            //加载KEY-VALUE配置
            Properties properties = new Properties();
            properties.load(new ByteArrayInputStream(config.getBytes()));
            CollectionUtils.mergePropertiesIntoMap(properties, DynamicProperties.staticProperties);
        }
    }

    void loadLogConfig(String fileName, String config) throws IOException {
        String filePath = ExtendPropertiesFactoryBean.class.getResource("/").getPath() + fileName;
        FileOutputStream out = null;
        PrintWriter writer = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
            out = new FileOutputStream(file);

            BufferedOutputStream stream = new BufferedOutputStream(out);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream, "UTF-8")));
            writer.write(config);
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
            if (out != null) {
                out.close();
            }
        }

        PropertyConfigurator.configure(filePath);//刷新配置文件
        if (logger.isDebugEnabled()) {
            System.out.println("debug!!");
        }
        if (logger.isInfoEnabled()) {
            System.out.println("info!!");
        }
        if (logger.isErrorEnabled()) {
            System.out.println("error!!");
        }
    }

    @Override
    public Properties createProperties() throws IOException {
/*        String[*//*groupId*//*][*//*dataId*//*] configs = new String[][]{
                {"aicai", "dubbo"},
                {"aicai", "filter"},
                {"aicai_backend", "baseconfig"},
                {"aicai_backend", "configurl"},
                {"aicai_backend", "redis"},
                {"aicai_backend", "logconfig"}};*/
        if (configs == null || configs.length == 0) {
            throw new NullPointerException("请配置configs！");
        }
        System.out.println(com.alibaba.fastjson.JSON.toJSONString(configs));
        loadConfig(configs);
        this.setProperties(DynamicProperties.staticProperties);
        return this.mergeProperties();
    }

    public void setConfigs(String[][] configs) {
        this.configs = configs;
    }

    private String[/*groupId*/][/*dataId*/] configs;

}
