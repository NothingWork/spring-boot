package com.ysu.service.Impl;

import com.ysu.domain.Repair;
import com.ysu.domain.Result;
import com.ysu.mapper.RepairMapper;
import com.ysu.service.IRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yun
 * @version 1.0
 * @description: 实现与设备报修有关的功能接口
 * @date 2023/9/3
 */
@Service
public class RepairServiceImpl implements IRepairService {
    @Autowired
RepairMapper repairMapper;
/**
 * @author Yun
 * @description: 添加新的报修单
 * @param: repair
 * @return: com.ysu.domain.Result
 * @date: 2023/9/3
 *
 */
@Override
    public Result addRepair(Repair repair) {
        repairMapper.addRepair(repair);
        return new Result(1,"success",null);
    }
/**
 * @author Yun
 * @description: 获取所有的报修记录
 * @param: null
 * @return: com.ysu.domain.Result
 * @date: 2023/9/4
 */
@Override
    public Result getRecords() {
    List<Repair> list = repairMapper.getRecords();
    if(null!=list){
        return new Result(1,"success",list);
    }
    return new Result(1,"failed",null);
    }
}
