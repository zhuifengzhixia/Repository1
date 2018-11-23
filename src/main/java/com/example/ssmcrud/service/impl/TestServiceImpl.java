package com.example.ssmcrud.service.impl;

import com.example.ssmcrud.dto.Department;
import com.example.ssmcrud.dto.Employee;
import com.example.ssmcrud.mapper.DepartmentMapper;
import com.example.ssmcrud.mapper.EmployeeMapper;
import com.example.ssmcrud.service.TestService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TestServiceImpl implements TestService {


    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private SqlSession sqlSession;

    @Override
    public void Test() {

//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));
//        System.out.println("success");


//        employeeMapper.insertSelective(new Employee(null,"Jerry","M","Jerry@czu.com",1));

        //批量插入多个员工
//        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//        for(int i=0; i<1000; i++){
//
//            String uid = UUID.randomUUID().toString().substring(0,5) + i;
//
//            mapper.insertSelective(new Employee(null,uid,"M",uid+"@czu.com",1));
//        }
//        System.out.println("批量完成");




    }

    @Override
    public void TestPageInfo(PageInfo pageInfo) {

        System.out.println("当前页码：" + pageInfo.getPageNum());
        System.out.println("总页码：" +pageInfo.getPages());
        System.out.println("总记录数：" +pageInfo.getTotal());
        System.out.println("在页面需要连续显示的页码：");
        int[] nums = pageInfo.getNavigatepageNums();
        for (int i : nums){
            System.out.print(" " + i);
        }

        //获取员工数据
        List<Employee> list = pageInfo.getList();
        for (Employee employee : list){
            System.out.println(employee.toString());
        }
    }
}
