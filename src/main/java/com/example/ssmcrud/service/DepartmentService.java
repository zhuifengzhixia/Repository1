package com.example.ssmcrud.service;

import com.example.ssmcrud.dto.Department;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {
    /**
     * 获取部门信息
     * @return
     */
    List<Department> getDepts();
}
