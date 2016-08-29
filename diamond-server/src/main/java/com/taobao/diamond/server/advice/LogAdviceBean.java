package com.taobao.diamond.server.advice;

import com.taobao.diamond.server.service.LogInfoService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 记录日志通知类
 * Created by Admin on 2016/8/15.
 */
@Aspect
@Component
public class LogAdviceBean {

    @Autowired
    LogInfoService logInfoService;

    //切面：新增配置
    @Pointcut("execution(* com.taobao.diamond.server.service.ConfigService.addConfigInfo(String,String,*,String,String))"
            + "&& args(dataId,group,*,userName,actionType)")
    public void getAddPointcut(String dataId, String group, String userName, String actionType) {
    }

    //切面：修改配置
    @Pointcut("execution(* com.taobao.diamond.server.service.ConfigService.updateConfigInfo(String,String,*,String,String))"
            + "&& args(dataId,group,*,userName,actionType)")
    public void getUpdatePointcut(String dataId, String group, String userName, String actionType) {
    }

    //切面：删除配置
    @Pointcut("execution(* com.taobao.diamond.server.service.ConfigService.removeConfigInfo(long,String,String))"
            + "&& args(id,userName,actionType)")
    public void getDelPointcut(long id, String userName, String actionType) {
    }

    @After("getAddPointcut(dataId,group,userName,actionType)||getUpdatePointcut(dataId,group,userName,actionType)")
    public void recordAddOrUpdateLog(String dataId, String group, String userName, String actionType) {
        StringBuffer sb = new StringBuffer("dataId=");
        sb.append(dataId);
        sb.append(" group=");
        sb.append(group);
        logInfoService.addLogInfo(userName, actionType, sb.toString());
    }

    @After("getDelPointcut( id,  userName, actionType)")
    public void recordDelLog(long id, String userName, String actionType) {
        StringBuffer sb = new StringBuffer("id=");
        sb.append(id);
        logInfoService.addLogInfo(userName, actionType, sb.toString());
    }

}
