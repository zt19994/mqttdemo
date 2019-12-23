package com.mqtt.mqttserver.controller;

import com.mqtt.mqttserver.util.ServerMqttUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.*;

/**
 * mq 服务器控制层
 *
 * @author zt1994 2019/12/23 16:18
 */
@RestController
public class MqServerController {

    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    @ResponseBody
    public String sendMsg(@RequestParam String msg) {
        String topic = "TOPIC/ID/0";
        String clientId = "server1";
        ServerMqttUtil mqttUtil = new ServerMqttUtil(topic);
        MqttMessage message = mqttUtil.getMessage(clientId, msg);
        mqttUtil.publish(message);
        return "send mqtt msg";
    }
}
