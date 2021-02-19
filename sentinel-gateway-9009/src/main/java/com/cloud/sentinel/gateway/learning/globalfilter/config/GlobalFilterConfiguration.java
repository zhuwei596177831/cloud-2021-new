package com.cloud.sentinel.gateway.learning.globalfilter.config;

import com.cloud.sentinel.gateway.learning.globalfilter.CustomGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 朱伟伟
 * @date 2021-02-19 16:50:51
 * @description
 */
@Configuration(proxyBeanMethods = false)
public class GlobalFilterConfiguration {

    @Bean
    public CustomGlobalFilter customGlobalFilter() {
        return new CustomGlobalFilter();
    }

}
