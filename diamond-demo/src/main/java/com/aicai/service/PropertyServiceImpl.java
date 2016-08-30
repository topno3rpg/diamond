package com.aicai.service;

import com.aicai.mapper.PropertyMapper;
import com.aicai.po.PropertyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Admin on 2016/8/4.
 */

@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyMapper propertyMapper;

    public void insert(PropertyPo obj) {
        propertyMapper.insert(obj);
    }

}
