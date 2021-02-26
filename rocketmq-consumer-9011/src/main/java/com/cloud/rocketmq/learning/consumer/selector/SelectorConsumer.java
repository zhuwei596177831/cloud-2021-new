package com.cloud.rocketmq.learning.consumer.selector;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-02-26 10:00:17
 * @description
 */
public class SelectorConsumer {
    public static void main(String[] args) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("SelectorConsumer");
        try {
            consumer.setNamesrvAddr("localhost:9876");
            consumer.subscribe("TopicTest", MessageSelector.bySql("a between 0 and 3"));
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt msg : msgs) {
                        System.out.println(msg);
                        System.out.println(new String(msg.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
            System.out.println("consumer started");
        } catch (MQClientException e) {
            e.printStackTrace();
            System.out.println("consumer started failed");
        }
    }
}
