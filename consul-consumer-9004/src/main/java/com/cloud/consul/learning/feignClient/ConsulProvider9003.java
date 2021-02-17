package com.cloud.consul.learning.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-17 13:58:04
 * @description
 */
@FeignClient(name = "consul-provider-9003", path = "/consul-provider")
public interface ConsulProvider9003 {

    @GetMapping("/getResult")
    Map<String, String> getResult();

}
