package com.example.ssmcrud.dto;

public class User {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 登录名
     */
    private String username;

    /***
     * 密码
     */
    private String password;

    /**
     * 是否是管理员
     */
    private Integer admin;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }
}
