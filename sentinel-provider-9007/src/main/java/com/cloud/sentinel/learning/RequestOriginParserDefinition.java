package com.cloud.sentinel.learning;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 朱伟伟
 * @date 2021-02-22 14:38:06
 * @description 黑白名单
 */
@Component
public class RequestOriginParserDefinition implements RequestOriginParser {
    @Override
    public String parseOrigin(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
