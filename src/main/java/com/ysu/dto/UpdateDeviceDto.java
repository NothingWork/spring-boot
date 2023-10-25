package com.ysu.dto;

/**
 * @author Yun
 * @version 1.0
 * @description: 用于更改设备信息的包装对象
 * @date 2023/8/26
 */
public class UpdateDeviceDto {
    private Integer device_id;
    private String name;
    private String desc;

    private Integer user_id;

    public UpdateDeviceDto(Integer device_id, String name, String desc,Integer user_id) {
        this.device_id = device_id;
        this.name = name;
        this.desc = desc;
        this.user_id = user_id;
    }

    public Integer getDevice_id() {
        return device_id;
    }

    public void setDevice_id(Integer device_id) {
        this.device_id = device_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
