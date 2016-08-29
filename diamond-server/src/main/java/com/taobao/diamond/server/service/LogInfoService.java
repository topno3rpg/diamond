package com.taobao.diamond.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 记录日志服务
 * Created by Admin on 2016/8/15.
 */
@Service
public class LogInfoService {

    @Autowired
    private JdbcTemplate jt;

    public void addLogInfo(final String userName, final String actionType, final String comment) {
        this.jt.update(
                "insert into log_info (oper_name,action_type,comment) values(?,?,?)",
                new PreparedStatementSetter() {
                    public void setValues(PreparedStatement ps) throws SQLException {
                        int index = 1;
                        ps.setString(index++, userName);
                        ps.setString(index++, actionType);
                        ps.setString(index++, comment);
                    }
                });
    }

}
