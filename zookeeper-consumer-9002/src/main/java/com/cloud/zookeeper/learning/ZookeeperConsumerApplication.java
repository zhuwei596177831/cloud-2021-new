package com.cloud.zookeeper.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author 朱伟伟
 * @date 2021-02-17 13:43:54
 * @description
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.cloud.zookeeper.learning.feignClient"})
public class ZookeeperConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZookeeperConsumerApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }


}
