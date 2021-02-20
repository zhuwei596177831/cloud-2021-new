package com.cloud.sentinel.gateway.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.RouteMetadataUtils;
import org.springframework.context.annotation.Bean;

/**
 * @author 朱伟伟
 * @date 2021-02-19 14:50:11
 * @description
 */
@SpringBootApplication
public class SentinelGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelGatewayApplication.class, args);
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
