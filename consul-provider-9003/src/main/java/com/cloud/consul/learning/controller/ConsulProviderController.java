package com.cloud.consul.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-17 13:21:48
 * @description
 */
@RestController
public class ConsulProviderController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/serviceUrl")
    public List<ServiceInstance> serviceUrl() {
//        return discoveryClient.getServices().toString();
        return discoveryClient.getInstances("consul-provider-9003");
    }

    @GetMapping("/getResult")
    public Map<String, String> getResult() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "朱伟伟");
        map.put("time", Instant.now().toEpochMilli() + "");
        return map;
    }

}
