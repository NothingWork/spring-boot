package com.ysu.service;

import com.ysu.domain.Result;
import com.ysu.dto.LoginDto;
import com.ysu.dto.RegisterDto;
import com.ysu.dto.UpdateUserDto;
import org.springframework.stereotype.Service;

@Service("UserService")
public interface IUserService {
     Result Login(LoginDto loginDto);
     Result Register(RegisterDto registerDto);
     Result updateUser(UpdateUserDto updateUserDto);
}
