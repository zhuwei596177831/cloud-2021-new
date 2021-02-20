package com.cloud.sentinel.gateway.learning.globalfilter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-19 16:53:40
 * @description
 */
public class MyCustomGlobalFilter implements GlobalFilter, Ordered {
    private final Logger logger = LoggerFactory.getLogger(MyCustomGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("custom global filter");
        ServerHttpRequest request = exchange.getRequest();
        System.out.println(request.getMethodValue());
        System.out.println(request.getURI());//http://127.0.0.1:9009/nacos-consumer-9006/getResult
        System.out.println(request.getLocalAddress().getHostName());
        System.out.println(request.getLocalAddress().getHostString());
        System.out.println(request.getLocalAddress().getPort());
        System.out.println("************");
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        System.out.println(uri);//http://127.0.0.1:9006/nacos-consumer-9006/getResult
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        System.out.println(route.getUri());//http://127.0.0.1:9006
        Map<String, Object> metadata = route.getMetadata();
        for (Map.Entry<String, Object> entry : metadata.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        HttpHeaders httpHeaders = request.getHeaders();
        List<String> token = httpHeaders.getOrEmpty("token");
        if (token.isEmpty()) {
//            ServerHttpResponse response = exchange.getResponse();
//            response.setStatusCode(HttpStatus.UNAUTHORIZED);
//            response.getHeaders().add("Content-Type", "application/json");
//            return response.writeAndFlushWith(Flux.just(ByteBufFlux.just(response.bufferFactory().wrap(getWrapData()))));

            long epochMilli = Instant.now().toEpochMilli();
            exchange.getRequest().mutate().header("time", epochMilli + "");
            exchange.getRequest().mutate().header("token", getToken(epochMilli));
        }
        return chain.filter(exchange);
    }

    private String getToken(long epochMilli) {
        return DigestUtils.md5DigestAsHex((epochMilli + "CustomGlobalFilter").getBytes());
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
