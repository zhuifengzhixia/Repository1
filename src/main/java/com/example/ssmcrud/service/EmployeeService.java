package com.example.ssmcrud.service;

import com.example.ssmcrud.dto.Employee;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {


    List<Employee> getAll();

    Boolean saveEmpInfo(Employee employee);

    Boolean checkUser(String empName);

    Employee getEmpById(Integer id);

    Boolean updateEmp(Employee employee);

    Boolean deleteById(Integer id);

    Boolean massDeleteEmp(String strIds);
}
