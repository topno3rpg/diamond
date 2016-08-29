package com.aicai.something;

import com.taobao.diamond.client.DiamondClients;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Created by Admin on 2016/8/5.
 */
public class DiamondTest {

    public static void main(String[] args) {
        DiamondManager diamondManager = DiamondClients.createSafeDiamondManager("diamond", "test"
                , new ManagerListener() {
                    public void receiveConfigInfo(String configInfo) {
                        // 配置变更异步通知
                        try {
                            System.out.println("configInfo\n" + configInfo);
                            final Properties properties = new Properties();
                            properties.load(new ByteArrayInputStream(configInfo.getBytes()));
//                            properties.load(new StringReader(configInfo));

                            // ... ...

                        } catch (Throwable ignore) {
                        }
                    }

                    public Executor getExecutor() {
                        return null;
                    }
                }
        );
        String allConfig = diamondManager.getAvailableConfigureInfomation();
        System.out.println("allConfig====" + allConfig);
        Properties properties = new Properties();

        try {
            properties.load(new ByteArrayInputStream(allConfig.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
