package com.taobao.something;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;

/**
 * Created by Admin on 2016/8/5.
 */
public class JdbcPassword {

    @Test
    public void encryptPassword() throws Exception {
        System.out.println(ConfigTools.encrypt("e6xuSpU5gAtU6Wa4b2od"));
    }

}
