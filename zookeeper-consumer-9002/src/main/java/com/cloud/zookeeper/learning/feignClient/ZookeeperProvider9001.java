package com.cloud.zookeeper.learning.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-17 13:58:04
 * @description
 */
@FeignClient(name = "zookeeper-provider-9001", path = "/zookeeper-provider")
public interface ZookeeperProvider9001 {

    @GetMapping("/getResult")
    Map<String, String> getResult();

}
