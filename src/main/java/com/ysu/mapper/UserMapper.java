package com.ysu.mapper;

import com.ysu.domain.Device;
import com.ysu.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    /**
     * @author Yun
     * @description: 根据用户名查找用户数据
     * @param:用户名：username
     * @return 用户类
     * @date 2023/8/23 11:36
     */
     User findByUsername(String username);
/**
 * @author Yun
 * @description:  增加用户数据库实现注册功能
 * @param: 用户名：username,密码：password,邮箱：email
 * @return:注册是否成功
 * @date: 2023/8/24
 */
     User insertUser(String username,String password,String email);

/**
 * @author Yun
 * @description: 修改用户信息，更新数据库
 * @param: id，username，email
 * @return: 更新是否成功
 * @date: 2023/8/25
 */
     void updateUser(String username,String email,Integer id);

}
