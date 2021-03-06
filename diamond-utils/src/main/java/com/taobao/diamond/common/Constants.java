/*
 * (C) 2007-2012 Alibaba Group Holding Limited.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.
 * Authors:
 *   leiwen <chrisredfield1985@126.com> , boyan <killme2008@gmail.com>
 */
package com.taobao.diamond.common;

public class Constants {

    public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    public static final String BASE_DIR = "config-data";

    public static final String DEFAULT_DOMAINNAME = "config.aixuedai.com";

    public static final String DAILY_DOMAINNAME = "config.aixuedai.com";

    public static final int DEFAULT_PORT = 8888;

    public static final String NULL = "";

    public static final String DATAID = "dataId";

    public static final String GROUP = "group";

    public static final String LAST_MODIFIED = "Last-Modified";

    public static final String ACCEPT_ENCODING = "Accept-Encoding";

    public static final String CONTENT_ENCODING = "Content-Encoding";

    public static final String PROBE_MODIFY_REQUEST = "Probe-Modify-Request";

    public static final String PROBE_MODIFY_RESPONSE = "Probe-Modify-Response";

    public static final String PROBE_MODIFY_RESPONSE_NEW = "Probe-Modify-Response-New";

    public static final String CONTENT_MD5 = "Content-MD5";

    public static final String IF_MODIFIED_SINCE = "If-Modified-Since";

    public static final String SPACING_INTERVAL = "client-spacing-interval";

    public static final String OPAQUE = "opaque";

    public static final int POLLING_INTERVAL_TIME = 15;// 秒

    public static final int ONCE_TIMEOUT = 5000;// 毫秒

    public static final int CONN_TIMEOUT = 5000;// 毫秒

    public static final int RECV_WAIT_TIMEOUT = ONCE_TIMEOUT * 5;// 毫秒

    public static final int CLIENT_CHANNEL_MAX_IDLE_TIME_SECONDS = 120;// 秒

    public static final int MAX_CONTENT_LENGTH = 1024 * 1024;// 1MB

    public static final String HTTP_URI_FILE = "/config.co";

    public static final String HTTP_PROTOCOL_PREFIX = "http://";

    public static final String CONFIG_HTTP_URI_FILE = "/ServerNodes";

    public static final String ENCODE = "UTF-8";

    public static final String LINE_SEPARATOR = Character.toString((char) 1);

    public static final String WORD_SEPARATOR = Character.toString((char) 2);

    public static final int SC_OK = 200;
    public static final int SC_REQUEST_TIMEOUT = 408;

    /*
     * 批量操作时, 单条数据的状态码
     */
    // 发生异常
    public static final int BATCH_OP_ERROR = -1;
    // 查询成功, 数据存在
    public static final int BATCH_QUERY_EXISTS = 1;
    // 查询成功, 数据不存在
    public static final int BATCH_QUERY_NONEXISTS = 2;
    // 新增成功
    public static final int BATCH_ADD_SUCCESS = 3;
    // 更新成功
    public static final int BATCH_UPDATE_SUCCESS = 4;

    //日志常亮
    public static final String ADD_CONFIG = "ADD_CONFIG";
    public static final String UPDATE_CONFIG = "UPDATE_CONFIG";
    public static final String DEL_CONFIG = "DEL_CONFIG";
}
