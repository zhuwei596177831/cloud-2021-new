package com.cloud.sentinel.gateway.learning.gatewayfilter.config;

import com.cloud.sentinel.gateway.learning.gatewayfilter.CustomConsumerGatewayFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author 朱伟伟
 * @date 2021-02-19 17:49:41
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class GatewayFilterConfig {

    /**
     * @param builder:
     * @author: 朱伟伟
     * @date: 2021-02-19 17:50
     * @description: 绑定CustomConsumerGatewayFilter到指定路由
     **/
    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes().route(new Function<PredicateSpec, Route.AsyncBuilder>() {
//            @Override
//            public Route.AsyncBuilder apply(PredicateSpec predicateSpec) {
//                return predicateSpec.path("/nacos-consumer-9006/**").filters(new Function<GatewayFilterSpec, UriSpec>() {
//                    @Override
//                    public UriSpec apply(GatewayFilterSpec gatewayFilterSpec) {
//                        return gatewayFilterSpec.filter(new CustomConsumerGatewayFilter()).stripPrefix(0);
//                    }
//                }).uri("lb://CustomConsumerGatewayFilter").order(0).id("CustomConsumerGatewayFilter");
//
//            }
//        }).build();
        return builder.routes()
                .route(r ->
                        r.path("/nacos-consumer-9006/**")
                                .filters(
                                        f -> f.stripPrefix(1)
                                                .filters(new CustomConsumerGatewayFilter())
                                )
                                .uri("lb://nacos-consumer-9006")
                )
                .build();
    }


}
