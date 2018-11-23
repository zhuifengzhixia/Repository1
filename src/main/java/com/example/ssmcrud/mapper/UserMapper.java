package com.example.ssmcrud.mapper;

import com.example.ssmcrud.dto.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    User selectByUsername(@Param("username") String username);
}
