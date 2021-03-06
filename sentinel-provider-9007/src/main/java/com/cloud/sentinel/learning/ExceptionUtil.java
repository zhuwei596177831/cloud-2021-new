package com.cloud.sentinel.learning;

import com.alibaba.cloud.sentinel.rest.SentinelClientHttpResponse;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @author 朱伟伟
 * @date 2021-02-19 09:24:08
 * @description
 */
public class ExceptionUtil {

    public static ClientHttpResponse handleException(HttpRequest request,
                                                     byte[] body, ClientHttpRequestExecution execution, BlockException ex) {
        System.err.println("Oops: " + ex.getClass().getCanonicalName());
        return new SentinelClientHttpResponse("custom block info");
    }

}
