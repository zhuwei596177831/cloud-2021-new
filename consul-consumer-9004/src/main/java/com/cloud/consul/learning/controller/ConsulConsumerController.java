package com.cloud.consul.learning.controller;


import com.cloud.consul.learning.feignClient.ConsulProvider9003;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-17 13:47:05
 * @description
 */
@RestController
public class ConsulConsumerController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ConsulProvider9003 consulProvider9003;
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/getResult")
    public Map<String, String> getResult() {
        return restTemplate.getForObject("http://consul-provider-9003/consul-provider/getResult", Map.class);
    }


    @GetMapping("/getResultByOpenFeign")
    public Map<String, String> getResultByOpenFeign() {
        return consulProvider9003.getResult();
    }
}
