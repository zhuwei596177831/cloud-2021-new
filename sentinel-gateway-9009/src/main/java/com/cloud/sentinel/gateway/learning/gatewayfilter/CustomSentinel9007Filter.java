package com.cloud.sentinel.gateway.learning.gatewayfilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-02-20 10:35:16
 * @description 方式义一：自定义gatewayfilter 用于sentinel-9007
 */
public class CustomSentinel9007Filter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders httpHeaders = request.getHeaders();
        List<String> token = httpHeaders.get("sentinelToken");
        if (CollectionUtils.isEmpty(token)) {
            exchange.getRequest().mutate().header("sentinelToken", "sentinel9007TokenValue");
        }
        return chain.filter(exchange);
    }
}
