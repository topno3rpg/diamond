package com.taobao.diamond.server;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

import com.taobao.diamond.common.Constants;

public class DiamondServerTomcat {

    public static void main(String[] args) throws Exception {
        System.setProperty("profiles.active", "dev");

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(Constants.DEFAULT_PORT);
        tomcat.setBaseDir("target/tomcat");
        tomcat.getConnector().setURIEncoding("UTF-8");
        tomcat.getConnector().setAttribute("keepAliveTimeout", 2 * 60 * 1000);
        tomcat.getConnector().setAttribute("maxKeepAliveRequests", 200);

        Context ctx = tomcat.addWebapp("/", new File("src/main/webapp").getAbsolutePath());

        // declare an alternate location for your "WEB-INF/classes" dir:
        File additionWebInfClasses = new File("target/classes");
        VirtualDirContext resources = new VirtualDirContext();
        resources.setExtraResourcePaths("/WEB-INF/classes=" + additionWebInfClasses);
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
