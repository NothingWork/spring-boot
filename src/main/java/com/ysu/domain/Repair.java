package com.ysu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Yun
 * @version 1.0
 * @description: 报修单
 * @date 2023/9/3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Repair {
    private Integer user_id;
    private String phone;
    private String location;
    private String desc;
    private String date;
    private String type;
    private String pics;
}