package com.cloud.nacos.learning.nacosFeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-18 10:18:23
 * @description
 */
@FeignClient(name = "nacos-provider-9005", path = "/nacos-provider-9005")
public interface ProviderFeignClient {

    @GetMapping("/provider/{string}")
    Map<String, String> getResult(@PathVariable(value = "string") String value);
}
