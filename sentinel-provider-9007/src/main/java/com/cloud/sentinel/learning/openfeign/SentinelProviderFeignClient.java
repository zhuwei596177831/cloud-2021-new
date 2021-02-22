package com.cloud.sentinel.learning.openfeign;

import com.cloud.sentinel.learning.openfeign.config.SentinelFeignClientConfig;
import com.cloud.sentinel.learning.openfeign.fallback.SentinelFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-19 09:02:31
 * @description
 */
@FeignClient(
        name = "nacos-provider-9005",
        path = "/nacos-provider-9005",
        fallback = SentinelFeignClientFallback.class,
        configuration = SentinelFeignClientConfig.class)
public interface SentinelProviderFeignClient {

    @GetMapping("/provider/{string}")
    Map<String, String> getResult(@PathVariable(value = "string") String value);

}
