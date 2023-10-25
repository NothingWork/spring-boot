package com.ysu.service.Impl;

import com.ysu.domain.Device;
import com.ysu.domain.Result;
import com.ysu.dto.QueryDeviceDto;
import com.ysu.dto.UpdateDeviceDto;
import com.ysu.mapper.DeviceMapper;
import com.ysu.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Yun
 * @version 1.0
 * @description: 实现和设备有关的service接口
 * @date 2023/8/26
 */
@Service("Device")
public class DeviceServiceImpl implements IDeviceService {
    @Autowired
    DeviceMapper deviceMapper;

    /**
     * @author Yun
     * @description: 查询所有设备数据
     * @param: queryDeviceDto
     * @return: 查询结果
     * @date: 2023/8/26
     */
    @Override
    public Result queryDevices(QueryDeviceDto queryDeviceDto) {
        List<Device> list = deviceMapper.queryDevices(queryDeviceDto);
        if(list==null){
            return new Result(-7,"failed",null);
        }
        return new Result(1,"success",list);
    }

    /**
     * @author Yun
     * @description: 根据设备id更改设备状态
     * @param: device_id
        state
     * @return: com.ysu.domain.Result
     * @date: 2023/8/26
     * 因为更改失败的情况不存在，只保留了更新成功状态
     */
    @Override
    public Result changeState(Integer device_id, boolean state) {
        deviceMapper.changeState(device_id,state);
        return new Result(1,"success",null);
    }

    /**
     * @author Yun
     * @description: 根据设备id更改设备的名字和描述
     * @param: device_id
    name
    desc
     * @return: 更改信息的结果
     * @date: 2023/8/28
     */
    @Override
    public Result updateDevice(UpdateDeviceDto updateDeviceDto) {
        Device device = deviceMapper.findByDesc(updateDeviceDto.getDesc(),updateDeviceDto.getUser_id());
        //避免出现两个描述完全相同的设备，不利于用户分辨
        if(null==device || device.getDevice_id().equals(updateDeviceDto.getDevice_id())){
            deviceMapper.updateDevice(updateDeviceDto);
            return new Result(1,"success",null);
        }
        return new Result(-7,"failed",null);
    }

    @Override
    public Result insertDevice(Device device) {
        Device device1 = deviceMapper.findByDesc(device.getDesc(),device.getUser_id());
        //避免设备描述重复
        if(null==device1){
            deviceMapper.insertDevice(device);
            return new Result(1,"success",null);
        }
        return new Result(-7,"failed",null);
    }

    /**
     * @author Yun
     * @description: 模拟设备查找
     * @param: null
     * @return: com.ysu.domain.Result
     * @date: 2023/8/28
     */
    @Override
    public Result searchDevice() {
        //一个包含所有设备名称的数组
        List<String> list = new ArrayList<>();
        list.add("智慧一型设备");
        list.add("智慧二型设备");
        list.add("智慧三型设备");
        //生成一个随机数
        Random random = new Random();
        int num = random.nextInt(3);
        //模拟查找设备所需时间
        try {
            Thread.sleep(1000*(random.nextInt(3)+1));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //返回设备名称
        return new Result(1,"success", list.get(num));
    }

    /**
     * @author Yun
     * @description: 根据设备id添加设备
     * @param: device_id
     * @return: 删除结果
     * @date: 2023/8/28
     */
    @Override
    public Result removeDevice(Integer device_id) {
        deviceMapper.removeDevice(device_id);
        return new Result(1,"success",null);
    }
}
