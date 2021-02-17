package com.cloud.zookeeper.learning.controller;

import com.cloud.zookeeper.learning.feignClient.ZookeeperProvider9001;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-17 13:47:05
 * @description
 */
@RestController
public class ZookeeperConsumerController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    ZookeeperProvider9001 zookeeperProvider9001;

    @GetMapping("/getResult")
    public Map<String, String> getResult() {
        return restTemplate.getForObject("http://zookeeper-provider-9001/zookeeper-provider/getResult", Map.class);
    }

    @GetMapping("/getResultByOpenFeign")
    public Map<String, String> getResultByOpenFeign() {
        return zookeeperProvider9001.getResult();
    }
}
