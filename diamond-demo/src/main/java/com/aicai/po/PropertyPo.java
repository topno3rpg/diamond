package com.aicai.po;

import java.io.Serializable;

/**
 * Created by Admin on 2016/8/29.
 */
public class PropertyPo implements Serializable {

    private static final long serialVersionUID = 6588065261538915817L;

    private Integer id;
    private String fileName;
    private String projectName;
    private String key;
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
