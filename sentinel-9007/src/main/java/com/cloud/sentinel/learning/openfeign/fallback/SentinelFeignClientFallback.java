package com.cloud.sentinel.learning.openfeign.fallback;

import com.cloud.sentinel.learning.openfeign.SentinelFeignClient;

import java.util.Collections;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-19 09:04:14
 * @description
 */
public class SentinelFeignClientFallback implements SentinelFeignClient {
    @Override
    public Map<String, String> getResult(String value) {
        return Collections.singletonMap("msg", "SentinelFeignClientFallback");
    }
}
