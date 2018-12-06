package com.example.ssmcrud.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认接口处理
 */
@Controller
public class IndexController {

    /***
     * 默认访问
     * @return
     */
    @GetMapping("/")
    public String defaultPage(HttpServletRequest request){
        Object username = request.getSession().getAttribute("username");
        if(username == null || !(username instanceof String)){
            //未登录，转到登录页面
            return "redirect:/login";
        }
        return "redirect:/index";
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
}
