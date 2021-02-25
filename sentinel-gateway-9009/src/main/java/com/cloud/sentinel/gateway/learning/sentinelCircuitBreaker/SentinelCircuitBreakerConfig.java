package com.cloud.sentinel.gateway.learning.sentinelCircuitBreaker;

import com.alibaba.cloud.circuitbreaker.sentinel.ReactiveSentinelCircuitBreakerFactory;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-02-25 10:35:14
 * @description sentinel-gateway 熔断配置 未测试通过
 */
@Configuration(proxyBeanMethods = false)
public class SentinelCircuitBreakerConfig {

    @Bean
    public ReactiveSentinelCircuitBreakerFactory reactiveSentinelCircuitBreakerFactory() {
        return new ReactiveSentinelCircuitBreakerFactory();
    }

    @Bean
    public Customizer<ReactiveSentinelCircuitBreakerFactory> reactiveCircuitBreakerCustomizer() {
        String resourceName = "gateway-sentinel-provider-9007";
        List<DegradeRule> rules = Collections.singletonList(
                new DegradeRule(resourceName)
                        .setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO)
                        .setCount(0.6)
                        .setTimeWindow(10)
                        .setMinRequestAmount(5)
                        .setStatIntervalMs(10000)
        );
        return factory -> factory.configure(builder -> builder.rules(rules), resourceName);
    }

}
