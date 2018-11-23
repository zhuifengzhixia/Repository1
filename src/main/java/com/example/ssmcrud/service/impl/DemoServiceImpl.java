package com.example.ssmcrud.service.impl;


import com.example.ssmcrud.dto.Demo;
import com.example.ssmcrud.mapper.DemoMapper;
import com.example.ssmcrud.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoServiceImpl implements DemoService
{

    @Autowired
    private DemoMapper demoMapper;


    @Override
    public List<Demo> querybyid() {
        return demoMapper.querybyid();
    }
}
