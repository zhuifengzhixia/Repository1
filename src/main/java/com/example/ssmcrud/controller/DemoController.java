/***
 * created by weimeng on 2018/6/20
 */

package com.example.ssmcrud.controller;


import com.example.ssmcrud.dto.Demo;
import com.example.ssmcrud.dto.Department;
import com.example.ssmcrud.dto.Employee;
import com.example.ssmcrud.dto.User;
import com.example.ssmcrud.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class DemoController {


    @Autowired
    private DemoService demoService;

    @Autowired
    private TestService testService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    /***
     * 默认访问
     * @return
     */
    @GetMapping("/")
    public String defaultPage(){
        return "redirect:/login";
    }

    /***
     * 登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return "/login";
    }

    /**
     * 登录成功后进入index页面，即员工管理页面
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @RequestMapping("/hello2")
    public String hello2(){
        return "view/list";
    }

    @PostMapping("querybyid")
    @ResponseBody
    public List<Demo> querybyid(){

        return demoService.querybyid();
    }

    @RequestMapping("/test")
    @ResponseBody
    public void test(){
        testService.Test();
    }


    /***
     * 测试分页
     * @param pn
     * @return
     */
    @RequestMapping("/emps")
    @ResponseBody
    public String getEmps(@RequestParam(value = "pn",defaultValue = "1") Integer pn){

        //引入PageHelper分页插件
        //在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用PageInfo包装查询后的结果，只需要将pageInfo交给压面
        //封装了详细的分页信息，包括查询的结果,传入连续显示的页数
        PageInfo pageInfo = new PageInfo(emps, 5);

        //测试pageInfo
        testService.TestPageInfo(pageInfo);
        return "view/list";
    }


    /***
     * 返回页面传入分页信息
     * @param pn
     * @param model
     * @return
     */
    @RequestMapping("/query")
    public String query(@RequestParam(value = "pn",defaultValue = "1") Integer pn, Model model){

        //引入PageHelper分页插件
        //在查询之前只需要调用，传入页码，以及每页的大小
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用PageInfo包装查询后的结果，只需要将pageInfo交给压面
        //封装了详细的分页信息，包括查询的结果,传入连续显示的页数
        PageInfo pageInfo = new PageInfo(emps, 5);

        //将pageInfo信息传给页面
        model.addAttribute("pageInfo",pageInfo);

        return "view/list";
    }


    /****************************Employee*******************************************************/

    /***
     * 返回分页信息
     * @param pn
     * @return
     */
    @RequestMapping("/list")
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
    @RequestMapping("/get_dept_names")
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
     * 校验用户名是否可用
     * @param empName
     * @return
     */
    @PostMapping("/checkuser")
    @ResponseBody
    public Boolean checkUser(@RequestParam("empName") String empName){

        return employeeService.checkUser(empName);
    }


    /***
     * 根据id获取员工信息
     * @param id
     * @return
     */
    @RequestMapping("/emp/{id}")
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
    @PutMapping("/emp/{id}")
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
    @DeleteMapping("/emp/{id}")
    @ResponseBody
    public Boolean deleteById(@PathVariable("id") Integer id){
        return employeeService.deleteById(id);
    }


    /****
     * 批量删除
     * @param strIds
     * @return
     */
    @DeleteMapping("/emp/massdel")
    @ResponseBody
    public Boolean massDelete(@RequestParam("ids") String strIds){
        return employeeService.massDeleteEmp(strIds);
    }

    /***********************************************************************************/

    /******************************User*****************************************************/

    /***
     * 验证登录用户
     * @return
     */
    @PostMapping("/user/check_user_login")
    @ResponseBody
    public Integer checkUserLogin(User user, HttpServletRequest request){

        Integer result = userService.checkUserLogin(user);
        if(result == 0){
            //校验通过时，在session里放入一个标识
            //后续通过判断session里是否存在该标识来判断用户是否登录
            request.getSession().setAttribute("username", user.getUsername());
        }

        return result;
    }

    /***********************************************************************************/
}
