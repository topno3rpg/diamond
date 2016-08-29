package com.taobao.diamond.domain;

import java.util.Date;

/**
 * 日志类
 * Created by Admin on 2016/8/15.
 */
public class LogInfo {
    private Long id;
    private String operName;
    private Date createTime;
    private String actionType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
