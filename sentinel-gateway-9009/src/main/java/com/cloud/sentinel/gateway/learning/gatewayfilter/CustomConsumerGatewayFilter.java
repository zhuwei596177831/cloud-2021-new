package com.cloud.sentinel.gateway.learning.gatewayfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-02-19 17:44:30
 * @description
 */
public class CustomConsumerGatewayFilter implements GatewayFilter, Ordered {

    private final Logger logger = LoggerFactory.getLogger(CustomConsumerGatewayFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("CustomConsumerGatewayFilter");
//        ServerHttpRequest request = exchange.getRequest();
//        HttpHeaders httpHeaders = request.getHeaders();
//        List<String> token = httpHeaders.getOrEmpty("token");
//        if (token.isEmpty()) {
//            exchange.getRequest().mutate().header("nacos-consumer", "nacosConsumer9006");
//        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
