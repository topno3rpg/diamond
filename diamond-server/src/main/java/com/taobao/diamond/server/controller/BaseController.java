package com.taobao.diamond.server.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Admin on 2016/8/15.
 */
public class BaseController {

    public String getCurrentUser(HttpServletRequest request) {
        String userName = request.getSession().getAttribute("user").toString();
        return userName;
    }

}
