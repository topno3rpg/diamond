package com.aicai.service;

import com.aicai.mapper.XmlMapper;
import com.aicai.po.XmlPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2016/8/4.
 */

@Service("xmlService")
public class XmlServiceImpl implements XmlService {

    @Autowired
    protected XmlMapper xmlMapper;

    public void insert(XmlPo obj) {
        xmlMapper.insert(obj);
    }

}
