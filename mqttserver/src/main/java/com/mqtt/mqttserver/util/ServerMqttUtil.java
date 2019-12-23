package com.mqtt.mqttserver.util;

import com.mqtt.mqttclient.client.PushCallback;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送消息的服务端
 * 服务器向多个客户端推送主题，即不同客户端可向服务器订阅相同主题
 *
 * @author zt1994 2019/12/23 15:16
 */
@Data
public class ServerMqttUtil {

    private static final Logger logger = LoggerFactory.getLogger("ServerMqttUtil");

    /**
     * tcp://MQTT安装的服务器地址:MQTT定义的端口号
     */
    public static final String HOST = "tcp://127.0.0.1:1883";

    /**
     * 定义MQTT的ID，可以在MQTT服务配置中指定
     */
    private static final String CLIENT_ID = "server1";

    private MqttClient client;
    private MqttTopic mqttTopic;
    private String userName = "admin";
    private String passWord = "public";

    /**
     * 构造函数
     *
     * @throws MqttException
     */
    public ServerMqttUtil(String topic) {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        try {
            client = new MqttClient(HOST, CLIENT_ID, new MemoryPersistence());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        connect(topic);
    }


    /**
     * 用来连接服务器
     *
     * @param topic
     */
    private void connect(String topic) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.connect(options);
            mqttTopic = client.getTopic(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 构建mq发送消息
     *
     * @param clientId
     * @param msg
     * @return
     */
    public MqttMessage getMessage(String clientId, String msg) {
        MqttMessage mqttMessage = new MqttMessage();
        // 保证消息能到达一次
        mqttMessage.setQos(1);
        mqttMessage.setRetained(true);
        String str = "{\"clientId\":\"" + clientId + "\",\"msg\":\"" + msg + "\"}";
        mqttMessage.setPayload(str.getBytes());
        return mqttMessage;
    }


    /**
     * 发送消息并获取回执
     *
     * @param message
     */
    public void publish(MqttMessage message) {
        MqttDeliveryToken token = null;
        try {
            token = mqttTopic.publish(message);
            token.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        logger.info("message is published completely! " + token.isComplete());
        logger.info("messageId:" + token.getMessageId());
        token.getResponse();
        if (client.isConnected()) {
            try {
                client.disconnect(10000);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
        logger.info("Disconnected: delivery token \"" + token.hashCode() + "\" received: " + token.isComplete());
        logger.info(message.isRetained() + "------ratained状态");
    }


}
