package com.aicai.controller;

import com.aicai.po.StudentPo;
import com.aicai.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PreferencesPlaceholderConfigurer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    StudentService studentService;

    @ResponseBody
    @RequestMapping({"/stu/insert"})
    public String insert() {
        StudentPo po = new StudentPo();
        po.setName("test");
        po.setAge(20);
        studentService.insert(po);
        return "ok";
    }

    @ResponseBody
    @RequestMapping({"/stu/select"})
    public List<StudentPo> select() {
        List<StudentPo> list = studentService.select();
        return list;
    }

    @ResponseBody
    @RequestMapping({"/value/dynamic"})
    public String getDynamicValue() {
        return studentService.getDynmicValue();
    }

}