package com.cloud.sentinel.zuul.learning;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.DefaultBlockFallbackProvider;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.cloud.sentinel.zuul.learning.fallbackprovider.MyZuulBlockFallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * @author 朱伟伟
 * @date 2021-02-19 10:59:24
 * @description
 */
@SpringBootApplication
@EnableZuulProxy
public class SentinelZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelZuulApplication.class, args);
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-19 14:12
     * @description: 自定义sentinel整合zuul限流fallback
     * 发生限流之后可自定义返回参数，通过实现 SentinelFallbackProvider 接口，
     * 默认的实现是 DefaultBlockFallbackProvider。
     **/
    @Bean
    public ZuulBlockFallbackProvider zuulBlockFallbackProvider() {
        return new MyZuulBlockFallbackProvider();
    }


}
