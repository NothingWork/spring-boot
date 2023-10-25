package com.ysu.controller;

import com.ysu.domain.Result;
import com.ysu.dto.LoginDto;
import com.ysu.dto.RegisterDto;
import com.ysu.dto.UpdateUserDto;
import com.ysu.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author user
 * @version 1.0
 * @description: 处理跟用户登录注册等有关业务
 * @date 2023/8/23 11:35
 */
@RestController
public class UserController {
    @Autowired
    IUserService userService;
//登录
    @RequestMapping("/api/login")
    public Result Login(@RequestBody LoginDto loginDto){
        return userService.Login(loginDto);
    }
//注册
    @RequestMapping("/api/register")
    public Result Register(@RequestBody RegisterDto registerDto){
        return userService.Register(registerDto);}
//修改
    @RequestMapping("/api/update")
    public Result UpdateUser(@RequestBody UpdateUserDto updateUserDto){
        return userService.updateUser(updateUserDto);
    }
}
