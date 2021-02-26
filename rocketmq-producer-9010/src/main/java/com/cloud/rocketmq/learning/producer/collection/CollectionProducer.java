package com.cloud.rocketmq.learning.producer.collection;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-02-26 09:21:20
 * @description
 */
public class CollectionProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("collectionProducer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        producer.setRetryTimesWhenSendFailed(0);
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Message message = new Message("TopicTest", (i + ":zww").getBytes(StandardCharsets.UTF_8));
            messages.add(message);
        }
        producer.send(messages);
        producer.shutdown();
    }
}
