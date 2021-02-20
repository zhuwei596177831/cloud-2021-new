package com.cloud.sentinel.gateway.learning.globalfilter.config;

import com.cloud.sentinel.gateway.learning.globalfilter.MyCustomGlobalFilter;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;

import java.security.Principal;

/**
 * @author 朱伟伟
 * @date 2021-02-19 16:50:51
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class GlobalFilterConfiguration {

    @Bean
    public MyCustomGlobalFilter myCustomGlobalFilter() {
        return new MyCustomGlobalFilter();
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-20 11:28
     * @description: pre GlobalFilter
     **/
//    @Bean
//    public GlobalFilter customGlobalPreFilter() {
//        return (exchange, chain) -> exchange.getPrincipal()
//                .map(Principal::getName)
//                .defaultIfEmpty("Default User")
//                .map(userName -> {
//                    //adds header to proxied request
//                    exchange.getRequest().mutate().header("CUSTOM-REQUEST-HEADER", userName).build();
//                    return exchange;
//                })
//                .flatMap(chain::filter);
//    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-20 11:28
     * @description: post GlobalFilter
     **/
//    @Bean
//    public GlobalFilter customGlobalPostFilter() {
//        return (exchange, chain) -> chain.filter(exchange)
//                .then(Mono.just(exchange))
//                .map(serverWebExchange -> {
//                    //adds header to response
//                    serverWebExchange.getResponse().getHeaders().set("CUSTOM-RESPONSE-HEADER",
//                            HttpStatus.OK.equals(serverWebExchange.getResponse().getStatusCode()) ? "It worked" : "It did not work");
//                    return serverWebExchange;
//                })
//                .then();
//    }

}
