package com.aicai.service;

import com.aicai.po.StudentPo;

import java.util.List;

/**
 * Created by Admin on 2016/8/4.
 */
public interface StudentService {

    public void insert(StudentPo obj);

    public List<StudentPo> select();

    public String getDynmicValue();

}
