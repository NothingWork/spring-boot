package com.ysu.dto;


/**
 * @author Yun
 * @version 1.0
 * @description: 用于模糊查询的包装对象
 * @date 2023/8/26
 */

public class QueryDeviceDto {
    //用户id
    private Integer user_id;
    //查询内容
    private String queryInfo;
    //页面包含数据数量

    public QueryDeviceDto(Integer user_id, String queryInfo) {
        this.user_id = user_id;
        this.queryInfo = queryInfo;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getQueryInfo() {
        return queryInfo;
    }

    public void setQueryInfo(String queryInfo) {
        this.queryInfo = queryInfo;
    }
}
