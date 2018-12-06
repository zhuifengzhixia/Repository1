package com.example.ssmcrud.controller;

import com.example.ssmcrud.dto.User;
import com.example.ssmcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


/***
 * 用户接口处理
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;




    /***
     * 验证登录用户
     * @return
     */
    @PostMapping("/check_user_login")
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

}
