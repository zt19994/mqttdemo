package com.mqtt.mqttclient.client;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * @author zt1994 2019/12/23 10:10
 */
public class MqClient {

    public static final String TOPIC = "TOPIC/ID/0";
    private static final String clientId = "CLIENT_ID_1";
    private static final String serverURI = "tcp://127.0.0.1:1883";
    private MqttClient client;
    private MqttConnectOptions options;
    private String userName = "admin";
    private String passWord = "public";

    public void start() {
        try {
            client = new MqttClient(serverURI, clientId, new MemoryPersistence());
            options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(userName);
            options.setPassword(passWord.toCharArray());
            options.setConnectionTimeout(10000000);
            options.setKeepAliveInterval(60000000);
            // MQ 回调函数
            client.setCallback(new PushCallback());
            client.connect(options);
            int[] qos = {1};
            String[] topic1 = {TOPIC};
            client.subscribe(topic1, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MqttException {
        MqClient client = new MqClient();
        client.start();
    }

}
