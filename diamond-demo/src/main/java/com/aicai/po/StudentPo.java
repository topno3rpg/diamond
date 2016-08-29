package com.aicai.po;

import java.io.Serializable;

/**
 * Created by Admin on 2016/8/4.
 */
public class StudentPo implements Serializable{

    private static final long serialVersionUID = -1380382421051301183L;
    private Long id;
    private String name;
    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
