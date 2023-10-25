package com.ysu.dto;

/**
 * @author Yun
 * @version 1.0
 * @description: 用于接收前端注册数据的实体类
 * @date 2023/8/24 9:00
 */
public class RegisterDto {
    private String username;
    private String password;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
