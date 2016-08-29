package com.aicai.test.start;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.naming.resources.VirtualDirContext;

public class DiamondDemoTomcat {

    public static void main(String[] args) throws Exception {

        String webappPath = new File("src/main/webapp").getAbsolutePath();
        // String
        // webappPath="G:\\axd-project\\aicai-project\\aicai-diamondtest";
        System.out.println("webappPath ==== " + webappPath);

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8081);
        tomcat.setBaseDir("target/tomcat");
        Context ctx = tomcat.addWebapp("/demo", webappPath);
        tomcat.getConnector().setURIEncoding("UTF-8");

        // declare an alternate location for your "WEB-INF/classes" dir:
        File additionWebInfClasses = new File("target/classes");
        VirtualDirContext resources = new VirtualDirContext();
        resources.setExtraResourcePaths("/WEB-INF/classes=" + additionWebInfClasses);
        ctx.setResources(resources);

        tomcat.start();
        tomcat.getServer().await();
    }
}
