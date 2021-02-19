package com.cloud.sentinel.learning;

import com.alibaba.cloud.sentinel.annotation.SentinelRestTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
public class SentinelApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentinelApplication.class, args);
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-19 9:22
     * @description: The attribute of the @SentinelRestTemplate annotation
     * support flow control(blockHandler, blockHandlerClass) and circuit breaking(fallback, fallbackClass).
     * <p>
     * The blockHandler or fallback is the static method of blockHandlerClass or fallbackClass.
     *
     * The parameter and return value of method in @SentinelRestTemplate is same as org.springframework.http.client.ClientHttpRequestInterceptor#interceptor,
     * but it has one more parameter BlockException to catch the exception by Sentinel.
     * {@link org.springframework.http.client.ClientHttpRequestInterceptor}
     **/
    @Bean
    @SentinelRestTemplate(blockHandler = "handleException", blockHandlerClass = ExceptionUtil.class,
            fallback = "fallback", fallbackClass = ExceptionUtil.class)
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

}
