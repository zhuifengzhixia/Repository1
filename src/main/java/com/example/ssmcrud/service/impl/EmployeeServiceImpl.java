package com.example.ssmcrud.service.impl;

import com.example.ssmcrud.dto.Employee;
import com.example.ssmcrud.dto.EmployeeExample;
import com.example.ssmcrud.mapper.EmployeeMapper;
import com.example.ssmcrud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService
{

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    @Override
    public Boolean saveEmpInfo(Employee employee) {
        if ( employeeMapper.insertSelective(employee) != 1){
            return false;
        }
        return  true;
    }

    /**
     * 校验用户名是否可用
     * @param empName
     * @return
     */
    @Override
    public Boolean checkUser(String empName) {

        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }

    /***
     * 根据id获取员工信息
     * @param id
     * @return
     */
    @Override
    public Employee getEmpById(Integer id) {
        return employeeMapper.selectByPrimaryKeyWithDept(id);
    }

    /**
     * 更新员工信息
     * @param employee
     * @return
     */
    @Override
    public Boolean updateEmp(Employee employee) {

        return employeeMapper.updateByPrimaryKeySelective(employee) == 1;
    }


    /**
     *
     * 根据id删除员工信息
     * @param id
     * @return
     */
    @Override
    public Boolean deleteById(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id) == 1;
    }


    /**
     * 批量删除员工信息（根据传入的id数组）
     * @param strIds
     * @return
     */
    @Override
    public Boolean massDeleteEmp(String strIds) {

        List<Integer> emp_ids = new ArrayList<>();
        String [] ids = strIds.split(",");
        for (String id: ids) {
            emp_ids.add(Integer.parseInt(id));
        }
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpIdIn(emp_ids);
        employeeMapper.deleteByExample(example);
        return true;
    }
}
