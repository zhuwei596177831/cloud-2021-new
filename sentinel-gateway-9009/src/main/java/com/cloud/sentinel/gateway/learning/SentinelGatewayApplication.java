package com.cloud.sentinel.gateway.learning;

import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.DefaultBlockRequestHandler;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 朱伟伟
 * @date 2021-02-19 14:50:11
 * @description
 */
@SpringBootApplication
public class SentinelGatewayApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SentinelGatewayApplication.class, args);
    }

    @Bean
    public BlockRequestHandler blockRequestHandler() {
        return new DefaultBlockRequestHandler();
    }

    /**
     * @param args:
     * @author: 朱伟伟
     * @date: 2021-02-25 10:20
     * @description:
     **/
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //// TODO: 2021-02-25 网关限流失效 暂未找到原因?
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        GatewayFlowRule rule = new GatewayFlowRule("gateway-sentinel-provider-9007");
//        rule.setCount(1);
//        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
//        rules.add(rule);
//        GatewayRuleManager.loadRules(rules);
    }

    /**
     * @param routeBuilder:
     * @author: 朱伟伟
     * @date: 2021-02-20 10:27
     * @description: per-route timeouts configuration using Java DSL 会覆盖yml配置
     **/
//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder routeBuilder) {
//        return routeBuilder.routes()
//                .route("nacos-consumer-9006", r -> {
//                    return
////                            r.host("*.somehost.org").and()
//                            r.path("/nacos-consumer-9006/**")
////                            .filters(f -> f.addRequestHeader("header1", "header-value-1"))
//                                    .uri("http://127.0.0.1:9006")
//                                    .metadata(RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR, 1000)
//                                    .metadata(RouteMetadataUtils.CONNECT_TIMEOUT_ATTR, 3000);
//                })
//                .build();
//    }

}
