package com.example.ssmcrud.service.impl;


import com.example.ssmcrud.dto.Department;
import com.example.ssmcrud.mapper.DepartmentMapper;
import com.example.ssmcrud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    /**
     * 获取部门信息
     * @return
     */
    @Override
    public List<Department> getDepts() {
        //查出的所有部门信息
        return departmentMapper.selectByExample(null);
    }
}
