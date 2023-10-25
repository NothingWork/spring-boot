package com.ysu.controller;

import com.ysu.component.MqttConsumer;
import com.ysu.component.callback.MqttConsumerCallback;
import com.ysu.domain.Result;
import org.apache.ibatis.annotations.Param;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 * @author Yun
 * @version 1.0
 * @description: 测试mqtt的消息发送
 * @date 2023/9/4
 */
@RestController
@RequestMapping("/api")
public class MqttController {

    //消息推送到mqtt，并返回前端接收到的消息
    @GetMapping("/publish")
    public Object push(@Param("topic") String topic,
                       @Param("msg")String msg){
        MqttConsumer.publish(topic, msg);
        return new Result(1,"success",null);
    }
    //订阅topic
    @GetMapping("/subscribe")
    public Object subscribe(@Param("topic") String topic,
                            @Param("qos")int qos){
        MqttConsumer.subscribe(topic,qos);
        return new Result(1,"success",null);
    }
    //获取设备端返回的消息
     @PostMapping("/getMessage")
    public Result getMessage(){
       String message = new String(MqttConsumer.getMessage().getPayload());
        return new Result(1,"success",message);
    }
}
