package com.ysu.component.callback;

import com.ysu.util.PropertiesUtil;
import org.eclipse.paho.client.mqttv3.*;
import java.util.Arrays;

/**
 * @author Yun
 * @version 1.0
 * @description: mqtt回调处理类
 * @date 2023/9/4
 */
public class MqttConsumerCallback implements MqttCallbackExtended {
    private MqttClient client;
    private MqttConnectOptions options;
    private String[] topic;
    private int[] qos;
    private MqttMessage message;

    //构造函数
    public MqttConsumerCallback(MqttClient client, MqttConnectOptions options, String[] topic, int[] qos,MqttMessage message) {
        this.client = client;
        this.options = options;
        this.topic = topic;
        this.qos = qos;
        this.message = message;
    }

    public MqttMessage getMessage() {
        return message;
    }

    /**
     * 断开重连
     */
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("MQTT连接断开，发起重连......");
        cause.printStackTrace();
        try {
            if (null != client && !client.isConnected()) {
                client.reconnect();
                System.out.println("尝试重新连接");
            } else {
                client.connect(options);
                System.out.println("尝试建立新连接");
            }
        } catch (Exception e) {
            System.out.println("断线重连时出错"+e);
            e.printStackTrace();
        }
    }

    /**
     * 接收到消息调用令牌中调用
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + Arrays.toString(topic));
    }

    /**
     * 消息处理,当需要接收由mqtt模拟发出的信息时使用,接收到消息后
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        try {
            System.out.println("收到topic:" + topic + " 消息：" + message);
            this.message = message;
        } catch (Exception e) {
            System.out.println("处理mqtt消息异常:" + e);
        }
    }

    /**
     * mqtt连接后订阅主题
     */
    @Override
    public void connectComplete(boolean b, String s) {
        try {
            if (null != topic && null != qos) {
                if (client.isConnected()) {
                    client.subscribe(topic, qos);
                    System.out.println("mqtt连接成功，客户端ID：" + PropertiesUtil.MQTT_CLIENT_ID);
                    System.out.println("--订阅主题:：" + Arrays.toString(topic));
                } else {
                    System.out.println("mqtt连接失败，客户端ID：" + PropertiesUtil.MQTT_CLIENT_ID);
                }
            }
        } catch (Exception e) {
            System.out.println("mqtt订阅主题异常:" + e);
        }
    }
}
