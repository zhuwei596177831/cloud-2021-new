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
 * @description Feign 对应的接口中的资源名策略定义：httpmethod:protocol://requesturl。
 * @FeignClient 注解中的所有属性，Sentinel 都做了兼容
 * SentinelProviderFeignClient 接口中方法 getResult 对应的资源名为
 * GET:http://nacos-provider-9005/nacos-provider-9005/echo/{str}。
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
