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
    private static final String log_config = "logconfig";
    private static final String log_config_file = "log4j";
    private static final String dubbo_config = "dubboconfig";
    private static final String dubbo_config_file = "dubbo";
    private static final String freemarker_config = "freemarker";
    private static final String config_suff = ".properties";
    private static final String config_charset = "UTF-8";
    private static final String system_config = "systemconfig";
    private static final String alipay_config = "alipay";
    private static final String rig_user_config = "rig_user";
    private static final String pass_user_config = "pass_user";

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
        if (log_config.equalsIgnoreCase(dataId)) {
            //加载日志配置
            String fileName = log_config_file + config_suff;
            loadLogConfig(fileName, config);
        } else if (dubbo_config.equalsIgnoreCase(dataId)) {
            //加载dubbo配置
            String fileName = dubbo_config_file + config_suff;
            loadLogConfig(fileName, config);
        } else if (freemarker_config.equalsIgnoreCase(dataId)) {
            //加载freemarker配置
            String fileName = freemarker_config + config_suff;
            loadLogConfig(fileName, config);
        } else if (system_config.equalsIgnoreCase(dataId)) {
            String fileName = system_config + config_suff;
            loadLogConfig(fileName, config);
        } else if (alipay_config.equalsIgnoreCase(dataId)) {
            String fileName = alipay_config + config_suff;
            loadLogConfig(fileName, config);
        } else if (rig_user_config.equalsIgnoreCase(dataId)) {
            String fileName = rig_user_config + config_suff;
            loadLogConfig(fileName, config);
        } else if (pass_user_config.equalsIgnoreCase(dataId)) {
            String fileName = pass_user_config + config_suff;
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
                file.createNewFile();
            }
            out = new FileOutputStream(file);

            BufferedOutputStream stream = new BufferedOutputStream(out);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream, config_charset)));
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
            System.out.println("log level is debug!");
        }
        if (logger.isInfoEnabled()) {
            System.out.println("log level is  info!");
        }
        if (logger.isErrorEnabled()) {
            System.out.println("log level is  error!");
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
