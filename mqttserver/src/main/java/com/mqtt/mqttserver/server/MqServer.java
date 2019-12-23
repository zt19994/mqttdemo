package com.mqtt.mqttserver.server;

import com.mqtt.mqttclient.client.PushCallback;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MQ 服务器
 *
 * @author zt1994 2019/12/23 10:03
 */
public class MqServer {
    public static final String HOST = "tcp://127.0.0.1:1883";
    public static final String TOPIC = "TOPIC/ID/0";
    private static final String clientId = "ID-0";
    private MqttClient client;
    private MqttTopic topic;
    private String userName = "admin";
    private String passWord = "public";

    private MqttMessage message;

    private String getMessage() throws Exception {
        return "{" +
                        "'GHR':'TOPIC/ID/0 测试发送'," +
                        "'SHR':'test'," +
                        "'}";
    }

    public MqServer() throws MqttException {
        client = new MqttClient(HOST, clientId, new MemoryPersistence());
        connect();
    }

    private void connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        options.setConnectionTimeout(200);
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new PushCallback());
            client.connect(options);
            topic = client.getTopic(TOPIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发布主题消息
     *
     * @param topic
     * @param message
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    public void publish(MqttTopic topic, MqttMessage message) throws MqttPersistenceException, MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! " + token.isComplete());
    }

    public static void main(String[] args) throws Exception {
        MqServer server = new MqServer();
        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload(server.getMessage().getBytes());
        server.publish(server.topic, server.message);
        System.out.println(server.message.isRetained() + "------ratained");
    }
}
