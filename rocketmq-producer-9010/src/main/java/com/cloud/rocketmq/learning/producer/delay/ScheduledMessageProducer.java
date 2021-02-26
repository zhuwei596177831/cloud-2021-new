package com.cloud.rocketmq.learning.producer.delay;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author 朱伟伟
 * @date 2021-02-25 18:16:48
 * @description
 */
public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("ConsumerDelayProducer");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.start();
        defaultMQProducer.setRetryTimesWhenSendFailed(0);
        for (int i = 0; i < 3; i++) {
            Message message = new Message("TopicTest", "delayTag", ("hello scheduled message:" + i).getBytes(StandardCharsets.UTF_8));
            //1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            // 设置延时等级2,这个消息将在5s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(2);
            defaultMQProducer.send(message);
        }
        defaultMQProducer.shutdown();
    }
}
