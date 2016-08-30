package com.aicai.service;

import java.util.List;

import com.aicai.extend.DynamicProperties;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aicai.mapper.StudentMapper;
import com.aicai.po.StudentPo;

/**
 * Created by Admin on 2016/8/4.
 */

@Service("studentService")
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentMapper studentMapper;

    @Value("${dynamic.value}")
    String dynamicValue;

    public void insert(StudentPo obj) {
        studentMapper.insert(obj);
    }

    public List<StudentPo> select() {
        return studentMapper.select();
    }

    public String getDynmicValue() {
        return "old-dynamicValue=" + this.dynamicValue + "\n new-dynamic=" + DynamicProperties.staticProperties.getProperty("dynamic.value");
    }
}
