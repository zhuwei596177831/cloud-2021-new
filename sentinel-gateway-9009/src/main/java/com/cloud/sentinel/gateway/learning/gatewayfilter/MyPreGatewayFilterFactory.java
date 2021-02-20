package com.cloud.sentinel.gateway.learning.gatewayfilter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * @author 朱伟伟
 * @date 2021-02-20 13:33:36
 * @description 方式义二：自定义 PreGatewayFilterFactory
 */
public class MyPreGatewayFilterFactory extends AbstractGatewayFilterFactory<MyPreGatewayFilterFactory.Config> {

    public MyPreGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            //If you want to build a "pre" filter you need to manipulate the
            //request before calling chain.filter
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            //use builder to manipulate the request
            return chain.filter(exchange.mutate().request(builder.build()).build());
        };
    }

    static class Config {
        //Put the configuration properties for your filter here
    }

}
