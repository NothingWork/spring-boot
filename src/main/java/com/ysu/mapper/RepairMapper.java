package com.ysu.mapper;

import com.ysu.domain.Repair;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RepairMapper {
    //添加报修单
    void addRepair(Repair repair);
    //查询所有报修记录
    List<Repair> getRecords();
}
