package com.jjtest.taskcenter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

@Component
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void send(String msg) {
        try {
            ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("test", msg);
            future.addCallback(result -> {
                LOGGER.info("生产者成功发送消息到topic:{} partition:{}的消息", result.getRecordMetadata().topic(),
                        result.getRecordMetadata().topic());
            }, error -> {
                LOGGER.error("生产者发送消失败，原因：{}", error.getMessage());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
