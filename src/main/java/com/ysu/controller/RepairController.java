package com.ysu.controller;

import com.ysu.domain.Repair;
import com.ysu.domain.Result;
import com.ysu.service.IRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author Yun
 * @version 1.0
 * @description: 和设备报修界面有关的接口功能
 * @date 2023/9/1
 */
@RestController
public class RepairController {
    @Autowired
    IRepairService repairService;
    //图片上传接口
    @RequestMapping("/api/saveImg")
    public Result saveImg(@RequestParam("file") MultipartFile file,
                          @RequestParam("filename") String filename,
                          @RequestParam("user_id") String user_id) {
        // 指定要保存路径
        String filePath = "E:/uploads/"+user_id;
        //检查文件是否存在，若不存在则创建对应文件夹
        File folder = new File(filePath);
        if(!folder.exists()){folder.mkdirs();}
        //拼接虚拟映射文件路径和文件名
        filePath = filePath+"/"+filename;
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        try {
            File dest = new File(filePath);
            file.transferTo(dest);//保存文件到对应目录
            //文件保存到物理地址后拼接虚拟映射文件路径和文件名用于返回前端
            filePath = "http://localhost:8088/image/"+user_id+"/"+filename;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("文件上传失败");
        }
            return new Result(1, "success", filePath);
    }
    //报修单添加
    @RequestMapping("/api/addRepair")
    public Result addRepair(@RequestBody Repair repair){
        return repairService.addRepair(repair);
    }
    //查询报修记录
    @GetMapping("/api/getRecords")
    public Result getRecords(){return repairService.getRecords();}
}