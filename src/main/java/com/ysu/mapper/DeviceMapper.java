package com.ysu.mapper;

import com.ysu.domain.Device;
import com.ysu.dto.QueryDeviceDto;
import com.ysu.dto.UpdateDeviceDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yun
 * @version 1.0
 * @description: 和设备有关的业务处理
 * @date 2023/8/26
 */
@Mapper
@Repository
public interface DeviceMapper {
    /**
     * @author Yun
     * @description: 查询设备信息
     * @param: void
     * @return: 查询结果设备实体类
     * @date: 2023/8/26
     */
    List<Device> queryDevices(QueryDeviceDto queryDeviceDto);
    /**
     * @author Yun
     * @description: 根据设备id更改设备的状态
     * @param: 设备id，设备被更改为什么状态
     * @return: null
     * @date: 2023/8/26
     */
    void changeState(Integer device_id,boolean state);
    /**
     * @author Yun
     * @description: 根据设备id更改设备的名字和描述
     * @param: device_id，name，desc
     * @return: null
     * @date: 2023/8/28
     */
    void updateDevice(UpdateDeviceDto updateDeviceDto);

    /**
     * @author Yun
     * @description: 根据设备描述查找对应用户下相同的设备
     * @param: Desc
     * @return: com.ysu.domain.Device
     * @date: 2023/8/28
     *
     */
    Device findByDesc(String desc,Integer user_id);
    /**
     * @author Yun
     * @description: 添加设备
     * @param: device
     * @return: void
     * @date: 2023/8/28
     *
     */
    void insertDevice(Device device);

    /**
     * @author Yun
     * @description: 删除设备
     * @param: device_id
     * @return: void
     * @date: 2023/8/28
     */
    void removeDevice(Integer device_id);
}
