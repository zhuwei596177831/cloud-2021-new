package com.cloud.rabbitmq.learning.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 朱伟伟
 * @date 2021-03-01 10:45:13
 * @description
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeName = "hello-exchange";
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);

        String queue = channel.queueDeclare().getQueue();

        String routingKey = "zww";

        channel.queueBind(queue, exchangeName, routingKey);

        while (true) {
            channel.basicConsume(queue, new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String routingKey = envelope.getRoutingKey();
                    String contentType = properties.getContentType();
                    System.out.println("消费的路由键：" + routingKey);
                    System.out.println("消费的内容类型：" + contentType);
                    long deliveryTag = envelope.getDeliveryTag();
                    //确认消息
                    channel.basicAck(deliveryTag, false);
                    System.out.println("消费的消息体内容：");
                    String bodyStr = new String(body, StandardCharsets.UTF_8);
                    System.out.println(bodyStr);
                }
            });
        }
    }
}
