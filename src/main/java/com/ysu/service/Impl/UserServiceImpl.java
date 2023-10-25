package com.ysu.service.Impl;

import com.ysu.domain.Result;
import com.ysu.domain.User;
import com.ysu.dto.LoginDto;
import com.ysu.dto.RegisterDto;
import com.ysu.dto.UpdateUserDto;
import com.ysu.mapper.UserMapper;
import com.ysu.service.IUserService;
import com.ysu.vo.LoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author user
 * @version 1.0
 * @description: 实现接口
 * @date 2023/8/23 11:28
 */
@Service("UserService")
public class UserServiceImpl implements IUserService {
   @Autowired
    UserMapper userMapper;

    /**
     * @author Yun
     * @description: 实现登陆方法，填写返回信息
     * @param loginDto
     * @return result
     * @date 2023/8/23
     */
    public Result Login(LoginDto loginDto){
        User user = userMapper.findByUsername(loginDto.getUsername());
    if(null !=user && user.getPassword().equals(loginDto.getPassword())){
        LoginVO loginVO = new LoginVO();
        //封装向前端返回的消息
        loginVO.setUser_id(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setEmail(user.getEmail());
        loginVO.setToken(UUID.randomUUID().toString()); //一个基于随机数生成的token
        return new Result(1,"success",loginVO);
    }
    return new Result(401,"failed",null);
    }

    /**
     * @author Yun
     * @description: 实现注册方法，返回是否成功
     * @param registerDto
     *  @return result
     * @date 2023/8/23
     */
    @Override
    public Result Register(RegisterDto registerDto) {
        //查找用户名是否重复
        User user = userMapper.findByUsername(registerDto.getUsername());
        if(null==user){
            userMapper.insertUser(registerDto.getUsername(),registerDto.getPassword(), registerDto.getEmail());
        return new Result(1,"success","reg_result:ok");
        }
        return new Result(-7,"failed",null);
    }
/**
 * @author Yun
 * @description: 修改用户信息，更新数据库
 * @param:
 * @return: 更新是否成功
 * @date: 2023/8/25
 *
 */
    @Override
    public Result updateUser(UpdateUserDto updateUserDto) {
        //查找用户名是否重复
        User user = userMapper.findByUsername(updateUserDto.getUsername());
        if(null==user || user.getId().equals(updateUserDto.getId())){
            userMapper.updateUser(updateUserDto.getUsername(),updateUserDto.getEmail(),updateUserDto.getId());
            return new Result(1,"success",null);
        }
        return new Result(-7,"failed",null);
    }


}
