package com.cloud.sentinel.learning.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 朱伟伟
 * @date 2021-02-22 14:11:07
 * @description
 */
@FeignClient(
        name = "sentinel-provider-9007",
        path = "/sentinel-provider-9007")
public interface SentinelConsumerFeignClient {

    @GetMapping("/testAuthorityRule")
    String testAuthorityRule();

}
