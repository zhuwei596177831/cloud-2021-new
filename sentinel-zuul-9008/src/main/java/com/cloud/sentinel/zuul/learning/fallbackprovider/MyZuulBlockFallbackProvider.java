package com.cloud.sentinel.zuul.learning.fallbackprovider;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.BlockResponse;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-19 14:10:56
 * @description
 */
public class MyZuulBlockFallbackProvider implements ZuulBlockFallbackProvider {
    @Override
    public String getRoute() {
        return "*";
    }

    @Override
    public BlockResponse fallbackResponse(String route, Throwable cause) {
        if (cause instanceof BlockException) {
            Map<String, Object> map = new HashMap<>(4);
            map.put("time", LocalDateTime.now());
            map.put("msg", "Sentinel block exception");
            return new BlockResponse(HttpStatus.TOO_MANY_REQUESTS.value(), JSON.toJSONString(map), route);
        } else {
            return new BlockResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), route);
        }
    }
}
