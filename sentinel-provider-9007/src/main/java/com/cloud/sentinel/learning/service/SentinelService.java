package com.cloud.sentinel.learning.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

/**
 * @author 朱伟伟
 * @date 2021-02-18 17:33:21
 * @description
 */
@Service
public class SentinelService {

    @SentinelResource(value = "testSentinel", blockHandler = "testSentinelBlockHandler", fallback = "testSentinelFallback")
    public String testSentinel() {
        return "Hello Sentinel";
    }

    public String testSentinelBlockHandler(BlockException e) {
        e.printStackTrace();
        return "testSentinel BlockHandler";
    }

    public String testSentinelFallback() {
        return "testSentinel fallback";
    }

}
