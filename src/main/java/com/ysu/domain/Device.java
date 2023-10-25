package com.ysu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yun
 * @version 1.0
 * @description: 设备实体类
 * @date 2023/8/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    private Integer device_id;
    private String name;
    private String desc;
    private boolean state;
    private Integer user_id;
}
