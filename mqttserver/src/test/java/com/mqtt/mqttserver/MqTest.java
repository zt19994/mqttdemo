package com.mqtt.mqttserver;

import com.mqtt.mqttserver.util.ServerMqttUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;

/**
 * MQ 测试
 *
 * @author zt1994 2019/12/23 15:24
 */
public class MqTest {

    /**
     * 测试mq
     */
    @Test
    public void mqTest() {

        String topic = "TOPIC/ID/0";
        String clientId = "client_id_1";

        ServerMqttUtil mqttUtil = new ServerMqttUtil(topic);
        MqttMessage message = mqttUtil.getMessage(clientId, "test 工具类");
        mqttUtil.publish(message);

    }
}
