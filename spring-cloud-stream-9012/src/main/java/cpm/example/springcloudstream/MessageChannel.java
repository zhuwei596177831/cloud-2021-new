package cpm.example.springcloudstream;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author 朱伟伟
 * @date 2021-03-12 09:47:39
 * @description
 */
public class MessageChannel {
    public static void main(String[] args) {
        BroadCasting();
    }

    private static void UniCasting() {
        //message单播
        SubscribableChannel subscribableChannel = new DirectChannel();
        subscribableChannel.subscribe(message -> {
            System.out.println("receive:" + message.getPayload());
        });

        subscribableChannel.subscribe(message -> {
            System.out.println("receive1:" + message.getPayload());
        });

        subscribableChannel.send(MessageBuilder.withPayload("哈哈哈").build());
        subscribableChannel.send(MessageBuilder.withPayload("fff").build());
    }

    private static void BroadCasting() {
        SubscribableChannel subscribableChannel = new PublishSubscribeChannel(Executors.newFixedThreadPool(10));
//        SubscribableChannel subscribableChannel = new PublishSubscribeChannel();
        subscribableChannel.subscribe(message -> {
            System.out.println("receive:" + message);
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        subscribableChannel.subscribe(message -> {
            System.out.println("receive1:" + message);
        });
        Map<String, Object> map = new HashMap<>();
        map.put("token", UUID.randomUUID().toString());
        MessageHeaders messageHeaders = new MessageHeaders(map);
//        subscribableChannel.send(MessageBuilder.createMessage("哈哈哈", messageHeaders));
        subscribableChannel.send(MessageBuilder.withPayload("哈哈哈").setHeader("token", UUID.randomUUID().toString()).build());
    }


}
