package com.cloud.sentinel.gateway.learning.globalfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.server.HttpServerRequest;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 朱伟伟
 * @date 2021-02-19 16:53:40
 * @description
 */
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    private final Logger logger = LoggerFactory.getLogger(CustomGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("custom global filter");
        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getMethodValue());
        System.out.println(request.getURI());
        HttpHeaders httpHeaders = request.getHeaders();
        List<String> token = httpHeaders.getOrEmpty("token");
        if (token.isEmpty()) {
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            response.getHeaders().add("Content-Type", "application/json");
            return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(getWrapData()))));
        }
        return chain.filter(exchange);
    }

    private byte[] getWrapData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", HttpStatus.UNAUTHORIZED.value());
        jsonObject.put("time", LocalDateTime.now());
        jsonObject.put("msg", "token invalid");
        return JSON.toJSONString(jsonObject, SerializerFeature.WriteDateUseDateFormat).getBytes(StandardCharsets.UTF_8);
    }


    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
