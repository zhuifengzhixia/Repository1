package com.example.ssmcrud.controller;


import com.example.ssmcrud.dto.Department;
import com.example.ssmcrud.dto.Employee;
import com.example.ssmcrud.service.DepartmentService;
import com.example.ssmcrud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/***
 * 员工接口处理
 */
@RestController
@RequestMapping("/emp")
public class EmployeeController {


    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;


    /***
     * 返回分页信息
     * @param pn
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public PageInfo list(@RequestParam(value = "pn",defaultValue = "1") Integer pn){

        //引入PageHelper分页插件
        //在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用PageInfo包装查询后的结果，只需要将pageInfo交给压面
        //封装了详细的分页信息，包括查询的结果,传入连续显示的页数
        PageInfo pageInfo = new PageInfo(emps, 5);

        return pageInfo;
    }


    /***
     * 获取部门信息
     * @return
     */
    @GetMapping("/get_dept_names")
    @ResponseBody
    public List<Department> getDepts(){

        return departmentService.getDepts();
    }


    /***
     * 保存新增员工信息
     * @return
     */
    @PostMapping("/add_emp_info")
    @ResponseBody
    public Boolean saveEmpInfo(Employee employee){

        return employeeService.saveEmpInfo(employee);
    }


    /**
     * 校验员工用户名是否可用
     * @param empName
     * @return
     */
    @PostMapping("/check_emp_name")
    @ResponseBody
    public Boolean checkUser(@RequestParam("empName") String empName){

        return employeeService.checkUser(empName);
    }


    /***
     * 根据id获取员工信息
     * @param id
     * @return
     */
    @RequestMapping("/{id}")
    @ResponseBody
    public Employee getEmpById(@PathVariable("id") Integer id){

        Employee employee =  employeeService.getEmpById(id);

        return employee;
    }


    /**
     * 根据id跟新员工信息
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    @ResponseBody
    public Boolean updateEmpById(@PathVariable("id") Integer id,
                                 Employee employee){
        employee.setEmpId(id);
        System.out.println(employee);
        return employeeService.updateEmp(employee);
    }


    /**
     * 根据id删除员工信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @ResponseBody
    public Boolean deleteById(@PathVariable("id") Integer id){
        return employeeService.deleteById(id);
    }


    /****
     * 批量删除
     * @param strIds
     * @return
     */
    @DeleteMapping("/massdel")
    @ResponseBody
    public Boolean massDelete(@RequestParam("ids") String strIds){
        return employeeService.massDeleteEmp(strIds);
    }

}
