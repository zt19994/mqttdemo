package com.mqtt.mqttclient.controller;

import com.mqtt.mqttclient.client.MqClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 项目自启
 *
 * @author zt1994 2019/12/23 16:42
 */
@Component
public class InitProject implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger(InitProject.class);

    @Override
    public void run(ApplicationArguments args){
        LOG.info("==========subscribe topic===========");
        MqClient client = new MqClient();
        client.start();
    }
}
