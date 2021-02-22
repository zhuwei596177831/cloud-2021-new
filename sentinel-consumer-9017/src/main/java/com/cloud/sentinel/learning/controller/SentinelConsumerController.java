package com.cloud.sentinel.learning.controller;

import com.cloud.sentinel.learning.openfeign.SentinelConsumerFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-02-22 14:09:25
 * @description
 */
@RestController
public class SentinelConsumerController {

    @Autowired
    SentinelConsumerFeignClient sentinelConsumerFeignClient;

    /**
     * @author: 朱伟伟
     * @date: 2021-02-22 14:13
     * @description: 访问控制规则 白名单、黑名单
     **/
    @GetMapping("/testAuthorityRule")
    public String testAuthorityRule() {
        return sentinelConsumerFeignClient.testAuthorityRule();
    }

}
