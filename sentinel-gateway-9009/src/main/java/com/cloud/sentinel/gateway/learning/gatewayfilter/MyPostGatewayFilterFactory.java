package com.cloud.sentinel.gateway.learning.gatewayfilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * @author 朱伟伟
 * @date 2021-02-20 13:35:36
 * @description 方式义二：自定义 PostGatewayFilterFactory
 */
public class MyPostGatewayFilterFactory extends AbstractGatewayFilterFactory<MyPostGatewayFilterFactory.Config> {

    public MyPostGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpResponse response = exchange.getResponse();
                //Manipulate the response in some way
            }));
        };
    }

    static class Config {
        //Put the configuration properties for your filter here
    }

}
