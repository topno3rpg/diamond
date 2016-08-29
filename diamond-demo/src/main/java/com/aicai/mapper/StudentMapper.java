package com.aicai.mapper;

import java.util.List;

import com.aicai.po.StudentPo;

public interface StudentMapper {

    public void insert(StudentPo obj);

    public void update(StudentPo obj);

    public List<StudentPo> select();

}
