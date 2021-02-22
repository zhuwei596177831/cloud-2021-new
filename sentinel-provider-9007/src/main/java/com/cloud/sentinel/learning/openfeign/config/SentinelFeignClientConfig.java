package com.cloud.sentinel.learning.openfeign.config;

import com.cloud.sentinel.learning.openfeign.fallback.SentinelFeignClientFallback;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;

/**
 * @author 朱伟伟
 * @date 2021-02-19 09:04:38
 * @description
 */
public class SentinelFeignClientConfig {

    @Bean
    public IRule iRule() {
        return new RandomRule();
    }

    @Bean
    public SentinelFeignClientFallback sentinelFeignClientFallback() {
        return new SentinelFeignClientFallback();
    }

}
