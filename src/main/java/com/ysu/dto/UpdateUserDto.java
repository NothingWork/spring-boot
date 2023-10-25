package com.ysu.dto;

/**
 * @author Yun
 * @version 1.0
 * @description: TODO
 * @date 2023/8/25
 */
public class UpdateUserDto {
    private Integer id;
    private String username;
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
