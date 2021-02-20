package com.cloud.sentinel.gateway.learning.gatewayfilter.config;

import com.cloud.sentinel.gateway.learning.gatewayfilter.CustomSentinel9007Filter;
import com.cloud.sentinel.gateway.learning.gatewayfilter.MyPostGatewayFilterFactory;
import com.cloud.sentinel.gateway.learning.gatewayfilter.MyPreGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.support.RouteMetadataUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱伟伟
 * @date 2021-02-20 10:35:59
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class GatewayFilterConfig {

    /**
     * @param routeBuilder:
     * @author: 朱伟伟
     * @date: 2021-02-20 10:42
     * @description: 自定义gatewayfilter 用于sentinel-9007
     **/
    @Bean
    public RouteLocator customSentinel9007RouteLocator(RouteLocatorBuilder routeBuilder) {
        return routeBuilder.routes()
                .route("sentinel-9007",
                        r -> r.path("/sentinel-9007/**")
                                .filters(f -> f.filter(new CustomSentinel9007Filter()))
                                .uri("http://127.0.0.1:9007")
                                .metadata(RouteMetadataUtils.RESPONSE_TIMEOUT_ATTR, 1000)
                                .metadata(RouteMetadataUtils.CONNECT_TIMEOUT_ATTR, 3000)
                )
//                .route(r -> r.path("/image/png")
//                        .filters(f ->
//                                f.addResponseHeader("X-TestHeader", "foobar"))
//                        .uri("http://httpbin.org:80")
//                )
                .build();
    }

//    @Bean
//    public MyPreGatewayFilterFactory myPreGatewayFilterFactory() {
//        return new MyPreGatewayFilterFactory();
//    }
//
//    @Bean
//    public MyPostGatewayFilterFactory myPostGatewayFilterFactory() {
//        return new MyPostGatewayFilterFactory();
//    }


}
