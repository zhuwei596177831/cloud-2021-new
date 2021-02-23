package com.cloud.sentinel.learning;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author 朱伟伟
 * @date 2021-02-18 16:23:05
 * @description
 */
@SpringBootApplication
@EnableFeignClients(basePackages = {"com.cloud.sentinel.learning.openfeign"})
@EnableDiscoveryClient
public class SentinelProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelProviderApplication.class, args);
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-23 13:34
     * @description: openfeign负载策略全局一个
     **/
    @Bean
    public IRule iRule() {
        return new RandomRule();
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-19 9:22
     * Spring Cloud Alibaba Sentinel 支持对 RestTemplate 的服务调用使用 Sentinel 进行保护，在构造 RestTemplate bean的时候需要加上 @SentinelRestTemplate 注解。
     * <p>
     * SentinelRestTemplate 注解的属性支持限流(blockHandler, blockHandlerClass)和降级(fallback, fallbackClass)的处理。
     * <p>
     * 其中 blockHandler 或 fallback 属性对应的方法必须是对应 blockHandlerClass 或 fallbackClass 属性中的静态方法。
     * 该方法的参数跟返回值跟 org.springframework.http.client.ClientHttpRequestInterceptor#interceptor 方法一致，
     * 其中参数多出了一个 BlockException 参数用于获取 Sentinel 捕获的异常。
     * <p>
     * {@link org.springframework.http.client.ClientHttpRequestInterceptor}
     **/
    @Bean
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}
