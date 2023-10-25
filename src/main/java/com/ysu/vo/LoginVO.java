package com.ysu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yun
 * @version 1.0
 * @description: 用于返回前端的数据，包含用户类和token
 * @date 2023/8/23 16:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {
    private Integer user_id;
    private String username;
    private String email;
    private String token;
}
