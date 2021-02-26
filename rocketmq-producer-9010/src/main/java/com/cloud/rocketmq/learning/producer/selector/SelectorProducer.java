package com.cloud.rocketmq.learning.producer.selector;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author 朱伟伟
 * @date 2021-02-26 09:55:59
 * @description
 */
public class SelectorProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("SelectorProducer");
        producer.setNamesrvAddr("localhost:9876");
        try {
            producer.start();
            producer.setRetryTimesWhenSendFailed(0);
            Message message = new Message("TopicTest", "SelectorProducer".getBytes());
            message.putUserProperty("a", "5");
            SendResult sendResult = producer.send(message);
            System.out.println(JSON.toJSONString(sendResult));
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
}
