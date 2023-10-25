package com.ysu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yun
 * @version 1.0
 * @description:  统一结果封装
 * @date 2023/8/23 10:15
 */
//生成get和set方法的注解
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    //响应码
    private Integer code;
    //信息
    private String message;
    //返回数据
    private Object data;
}
