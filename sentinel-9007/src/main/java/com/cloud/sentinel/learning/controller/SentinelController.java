package com.cloud.sentinel.learning.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cloud.sentinel.learning.openfeign.SentinelFeignClient;
import com.cloud.sentinel.learning.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-18 16:23:52
 * @description
 */
@RestController
public class SentinelController {

    @Autowired
    SentinelService sentinelService;
    @Autowired
    SentinelFeignClient sentinelFeignClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/testSentinel")
//    @SentinelResource(value = "testSentinel", blockHandler = "testSentinelBlockHandler", fallback = "testSentinelFallback")
    public String testSentinel() {
//        return "Hello Sentinel";
        return sentinelService.testSentinel();
    }

//    public String testSentinelBlockHandler(BlockException e) {
//        e.printStackTrace();
//        return "testSentinel BlockHandler";
//    }
//
//    public String testSentinelFallback() {
//        return "testSentinel fallback";
//    }

    @GetMapping("/getResultByOpenFeign")
    public Map getResultByOpenFeign() {
        return sentinelFeignClient.getResult("朱伟伟");
    }

    @GetMapping("/getResult")
    public Map getResult() {
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-provider-9005");
//        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-consumer-9006");
        //Access through the combination of LoadBalanceClient and RestTemplate
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider-9005");
//        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-consumer-9006");
        String path = String.format("http://%s:%s/nacos-provider-9005/provider/%s", serviceInstance.getHost(), serviceInstance.getPort(), "朱伟伟");
        System.out.println("request path:" + path);
        return restTemplate.getForObject(path, Map.class);
    }

}
