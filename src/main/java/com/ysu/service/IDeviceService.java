package com.ysu.service;

import com.ysu.domain.Device;
import com.ysu.domain.Result;
import com.ysu.dto.QueryDeviceDto;
import com.ysu.dto.UpdateDeviceDto;
import org.springframework.stereotype.Service;

@Service("Device")
public interface IDeviceService {
    Result queryDevices(QueryDeviceDto queryDeviceDto);
    Result changeState(Integer device_id,boolean state);
    Result updateDevice(UpdateDeviceDto updateDeviceDto);
    Result insertDevice(Device device);
    Result searchDevice();
    Result removeDevice(Integer device_id);
}
