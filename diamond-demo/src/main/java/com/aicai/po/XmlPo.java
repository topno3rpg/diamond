package com.aicai.po;

import java.io.Serializable;

/**
 * Created by Admin on 2016/8/29.
 */
public class XmlPo implements Serializable {

    private static final long serialVersionUID = -2221844353778878974L;

    private Integer id;
    private String fileName;
    private String projectName;
    private String varName;
    private String parentName;

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

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
