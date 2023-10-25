package com.ysu.service;


import com.ysu.domain.Repair;
import com.ysu.domain.Result;

public interface IRepairService {
    //新增报修
    Result addRepair(Repair repair);
    //查询报修记录
    Result getRecords();
}
