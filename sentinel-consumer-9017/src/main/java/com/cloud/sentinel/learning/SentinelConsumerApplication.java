package com.cloud.sentinel.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 朱伟伟
 * @date 2021-02-22 14:07:42
 * @description
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.cloud.sentinel.learning.openfeign"})
public class SentinelConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelConsumerApplication.class, args);
    }

}
