package com.ysu.controller;

import com.ysu.domain.Device;
import com.ysu.domain.Result;
import com.ysu.dto.QueryDeviceDto;
import com.ysu.dto.UpdateDeviceDto;
import com.ysu.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Yun
 * @version 1.0
 * @description: 设备信息接口
 * @date 2023/8/26
 */
@RestController
public class DeviceController {
    @Autowired
    IDeviceService deviceService;

    @GetMapping("/api/queryDevices")
    //查询所有数据
    public Result queryDevices(@RequestParam("user_id") Integer user_id,
                           @RequestParam("queryInfo") String queryInfo){
        //拼接 % 是为了匹配sql模糊查询语句
        QueryDeviceDto queryDeviceDto = new QueryDeviceDto(user_id,"%"+queryInfo+"%");
        return deviceService.queryDevices(queryDeviceDto);};
    //更改状态
    @RequestMapping ("/api/changeState")
    public Result changeState(@RequestParam("device_id") Integer device_id,
                              @RequestParam("state") boolean state){
        return deviceService.changeState(device_id,state);
    }
    //自定义名字和描述
    @RequestMapping("/api/updateDevice")
    public Result updateDevice(@RequestBody UpdateDeviceDto updateDeviceDto){
        return  deviceService.updateDevice(updateDeviceDto);
    }
    //添加设备
    @RequestMapping("/api/addDevice")
    public Result insertDevice(@RequestBody Device device){
        return deviceService.insertDevice(device);
    }
    //模拟设备查找
    @RequestMapping("/api/searchDevice")
    public Result searchDevice(){
        return deviceService.searchDevice();
    }
    //删除设备
    @RequestMapping("/api/removeDevice")
    public Result removeDevice(@RequestParam("device_id") Integer device_id){
        return deviceService.removeDevice(device_id);
    }
}

