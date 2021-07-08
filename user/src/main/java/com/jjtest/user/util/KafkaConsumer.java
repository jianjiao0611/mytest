package com.jjtest.user.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

//@Component
public class KafkaConsumer {

    // 消费监听
    @KafkaListener(topics = {"test"}, containerFactory = "ackContainerFactory")
    public void onMessage1(ConsumerRecord record, Acknowledgment ack) {
        // 消费的哪个topic、partition的消息,打印出消息内容
        System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "-" +  record.value());
        ack.acknowledge();
    }
}
