package com.cloud.nacos.learning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-18 09:39:28
 * @description
 */
@RestController
@RefreshScope
public class NacosProviderController {

    @Autowired
    DiscoveryClient discoveryClient;
    /**
     * 从 nacos config读取信息
     */
    @Value("${config.provider.name}")
    private String name;
    /**
     * nacos config 共享配置
     */
    @Value("${shared.provider01.name}")
    private String sharedName01;
    @Value("${shared.provider02.name}")
    private String sharedName02;
    /**
     * 本地yml
     */
    @Value("${config.provider.email}")
    private String email;
    @Value("${config.provider.address}")
    private String address;


    @GetMapping("/serviceUrl")
    public List<ServiceInstance> serviceUrl() {
        return discoveryClient.getInstances("nacos-provider-9005");
    }

    @GetMapping("/provider/{string}")
    public Map<String, String> getResult(@PathVariable(value = "string") String value) {
        Map<String, String> map = new HashMap<>();
        map.put("result", "Hello Nacos Consumer " + value);
        map.put("time", Instant.now().toEpochMilli() + "");
        map.put("name", name);
        map.put("email", email);
        map.put("address", address);
        map.put("sharedName01", sharedName01);
        map.put("sharedName02", sharedName02);
        return map;
    }

}
