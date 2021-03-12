package com.cloud.rabbitmq.learning.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author 朱伟伟
 * @date 2021-03-01 10:38:20
 * @description
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "hello-exchange";
//        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT);

        String routingKey = "zww";
        channel.basicPublish(exchangeName, routingKey, null, "朱伟伟".getBytes(StandardCharsets.UTF_8));

        channel.close();
        connection.close();
    }
}
