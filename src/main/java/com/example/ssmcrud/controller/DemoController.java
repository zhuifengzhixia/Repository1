/***
 * created by weimeng on 2018/6/20
 */

package com.example.ssmcrud.controller;


import com.example.ssmcrud.dto.Demo;
import com.example.ssmcrud.dto.Employee;
import com.example.ssmcrud.service.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DemoController {


    @Autowired
    private DemoService demoService;

    @Autowired
    private TestService testService;

    @Autowired
    private EmployeeService employeeService;



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

}
