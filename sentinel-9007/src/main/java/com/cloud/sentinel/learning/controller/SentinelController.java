package com.cloud.sentinel.learning.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.cloud.sentinel.learning.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-02-18 16:23:52
 * @description
 */
@RestController
public class SentinelController {

    @Autowired
    SentinelService sentinelService;

    @GetMapping("/testSentinel")
//    @SentinelResource(value = "testSentinel", blockHandler = "testSentinelBlockHandler", fallback = "testSentinelFallback")
    public String testSentinel() {
//        return "Hello Sentinel";
        return sentinelService.testSentinel();
    }

//    public String testSentinelBlockHandler(BlockException e) {
//        e.printStackTrace();
//        return "testSentinel BlockHandler";
//    }
//
//    public String testSentinelFallback() {
//        return "testSentinel fallback";
//    }

}
