package com.cloud.nacos.learning.controller;

import com.cloud.nacos.learning.nacosFeignClient.ProviderFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-18 09:46:27
 * @description
 */
@RestController
public class NacosConsumerController {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Autowired
    DiscoveryClient discoveryClient;
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    ProviderFeignClient providerFeignClient;
    @Autowired
    HttpServletRequest httpServletRequest;

    @GetMapping("/getResult")
    public Map getResult() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + "：" + httpServletRequest.getHeader(headerName));
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-provider-9005");
//        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-consumer-9006");
        //Access through the combination of LoadBalanceClient and RestTemplate
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider-9005");
//        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-consumer-9006");
        String path = String.format("http://%s:%s/nacos-provider-9005/provider/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);
        System.out.println("request path:" + path);
        return restTemplate.getForObject(path, Map.class);
    }

    @GetMapping("/getResultByOpenFeign")
    public Map getResultByOpenFeign() {
        return providerFeignClient.getResult("朱伟伟");
    }


}
