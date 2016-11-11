package com.aicai.something;

import com.alibaba.druid.filter.config.ConfigTools;
import com.sun.org.apache.xerces.internal.util.URI;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Admin on 2016/8/5.
 */
public class JdbcPassword {

    @Test
    public void encryptPassword() throws Exception {
        System.out.println(ConfigTools.encrypt("prEfA!aruzE3"));
    }

    @Test
    public void path() throws UnsupportedEncodingException {
        String filePath = JdbcPassword.class.getResource("/").getPath();
        filePath = "/G:/axd-project%20test/diamond-master/diamond-demo/target/test-classes/";
        System.out.println(URLDecoder.decode(filePath, "utf-8"));
    }
}
