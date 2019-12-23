package com.mqtt.mqttclient.client;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MQ 回调函数
 *
 * @author zt1994 2019/12/23 10:04
 */
public class PushCallback implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger("PushCallback");


    @Override
    public void connectionLost(Throwable throwable) {
        logger.info("--线程（ID：" + Thread.currentThread().getId() + "），连接丢失了");
        logger.info("连接缺失" + throwable);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        if (topic.endsWith("disconnected")) {
            logger.info("--线程(ID：" + Thread.currentThread().getId() + ").设备离线,离线原因为:" + mqttMessage.toString());
        } else if (topic.endsWith("connected")) {
            logger.info("--线程(ID:" + Thread.currentThread().getId() + "). 设备上线,连接信息:" + mqttMessage.toString());
        }
        logger.info("订阅到的主题 : " + topic);
        logger.info("发送等级 : " + mqttMessage.getQos());
        logger.info("发送的消息 : " + new String(mqttMessage.getPayload()));
        logger.info("源消息" + mqttMessage);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            logger.info("deliveryComplete---------" + iMqttDeliveryToken.isComplete() + "mqtt" + iMqttDeliveryToken.getMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
