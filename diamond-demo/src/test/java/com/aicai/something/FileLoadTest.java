package com.aicai.something;

import com.aicai.po.PropertyPo;
import com.aicai.po.XmlPo;
import com.aicai.service.PropertyService;
import com.aicai.service.XmlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 2016/8/29.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml", "classpath:spring-service.xml"})
public class FileLoadTest {

    @Test
    public void loadFile() throws IOException {
//        loadFilter(project_name);
//        loadProject();
    }

    public void loadFilter(String projectName) throws IOException {
        File file = new File(filter_path);
        File[] files = file.listFiles();
        for (File eachOne : files) {
            String fileName = eachOne.getName();
            Properties properties = new Properties();
            properties.load(new FileInputStream(eachOne));

            PropertyPo po = new PropertyPo();
            po.setProjectName(projectName);
            po.setFileName(fileName);
            Iterator<Map.Entry<Object, Object>> it = properties.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Object, Object> entry = it.next();
                String key = entry.getKey().toString();
                String value = entry.getValue().toString();
                po.setKey(key);
                po.setValue(value);
                propertyService.insert(po);
            }
            System.err.println(eachOne.getName());

        }
    }


    public void loadProject(String rootProjectName) throws IOException {
        File file = new File(project_path);
        File[] files = file.listFiles();
        for (File eachOne : files) {
            String projectName = eachOne.getName();
            if (projectName.indexOf("project") == -1) {
                continue;
            }
            File[] childFiles = eachOne.listFiles();
            for (File child : childFiles) {
                String childProjectName = child.getName();
                String path = child.getPath() + "\\bak";
                File bakFile = new File(path);
                File[] bakChildFiles = bakFile.listFiles();
                if (bakChildFiles == null) {
                    System.out.println("没有配置文件 path=" + path);
                    continue;
                }
                for (File bakChild : bakChildFiles) {
                    String bakchildFileName = bakChild.getName();
                    InputStream in = new FileInputStream(bakChild);
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        buffer.append(line);
                    }

                    String regEx = "\\$\\{(.*?)}";
                    Pattern pattern = Pattern.compile(regEx);
                    Matcher matcher = pattern.matcher(buffer.toString());
                    while (matcher.find()) {
                        XmlPo po = new XmlPo();
                        po.setProjectName(childProjectName);
                        po.setFileName(bakchildFileName);
                        po.setVarName(matcher.group(1));
                        po.setParentName(rootProjectName);
                        xmlService.insert(po);
                    }
                }
            }
        }
    }

    @Autowired
    PropertyService propertyService;

    @Autowired
    XmlService xmlService;

    String project_name = "aicai-project";
    String filter_path = "D:\\爱学贷项目\\配置中心\\aicai-project\\filter";
    String project_path = "D:\\爱学贷项目\\配置中心\\aicai-project";

}
