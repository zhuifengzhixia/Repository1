package com.example.ssmcrud.service.impl;

import com.example.ssmcrud.dto.User;
import com.example.ssmcrud.mapper.UserMapper;
import com.example.ssmcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;

    /***
     * 验证用户登录
     * 返回值： 0 正确；1 用户名不存在；2 密码错误
     * @param user
     * @return
     */
    @Override
    public Integer checkUserLogin(User user) {

        Integer result = 0;
        //1.验证用户名是否存在
        User returnUser = userMapper.selectByUsername(user.getUsername());
        if(returnUser != null){
            //2.若存在，验证密码是否正确
            if(!user.getPassword().equals(returnUser.getPassword())){
                result = 2;
            }
        }else {
            result = 1;
        }
        return result;
    }
}
