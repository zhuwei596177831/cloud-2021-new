package com.cloud.sentinel.learning.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.sentinel.learning.openfeign.SentinelProviderFeignClient;
import com.cloud.sentinel.learning.service.SentinelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author 朱伟伟
 * @date 2021-02-18 16:23:52
 * @description
 */
@RestController
public class SentinelProviderController {

    @Autowired
    SentinelService sentinelService;
    @Autowired
    SentinelProviderFeignClient sentinelProviderFeignClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    LoadBalancerClient loadBalancerClient;
    @Value("${spring.application.name}")
    private String appName;
    @Autowired
    HttpServletRequest httpServletRequest;

    /**
     * @author: 朱伟伟
     * @date: 2021-02-22 13:54
     * @description: 流量控制
     **/
    @GetMapping("/testSentinel")
//    @SentinelResource(value = "testSentinel", blockHandler = "testSentinelBlockHandler", fallback = "testSentinelFallback")
    public String testSentinel() {
//        return "Hello Sentinel";
        return sentinelService.testSentinel();
    }

    /**
     * @param param:
     * @author: 朱伟伟
     * @date: 2021-02-22 13:53
     * @description: 熔断
     **/
    @GetMapping("/testDegradeRule/{param}")
    @SentinelResource(value = "testDegradeRule", fallback = "testDegradeRuleFallback")
    public String testDegradeRule(@PathVariable(value = "param") Integer param) {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if (param < 0) {
            throw new RuntimeException("param不可为负");
        }
        return "testDegradeRule";
    }

    public String testDegradeRuleFallback(Integer param) {
        System.out.println("param：" + param);
        return "testDegradeRuleFallback";
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-22 13:54
     * @description: 访问控制规则 白名单、黑名单
     * @see com.cloud.sentinel.learning.RequestOriginParserDefinition
     **/
    @GetMapping("/testAuthorityRule")
    @SentinelResource(value = "testAuthorityRule", blockHandler = "testAuthorityRuleFallback")
    public String testAuthorityRule() {
        return "testAuthorityRule";
    }

    public String testAuthorityRuleFallback(BlockException e) {
        if (e instanceof AuthorityException) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpStatus.FORBIDDEN.value());
            jsonObject.put("msg", httpServletRequest.getRemoteAddr() + "被拉进黑名单");
            return jsonObject.toJSONString();
        }
        return "testAuthorityRuleFallback";
    }

    /**
     * @author: 朱伟伟
     * @date: 2021-02-22 15:31
     * @description: 热点参数
     **/
    @GetMapping("/testParamFlowRule/{goodsID}")
    @SentinelResource(value = "testParamFlowRule", blockHandler = "testParamFlowRuleFallback")
    public String testParamFlowRule(@PathVariable(value = "goodsID") String goodsID) {
        return "testAuthorityRule";
    }

    public String testParamFlowRuleFallback(String goodsID, BlockException e) {
        e.printStackTrace();
        if (e instanceof ParamFlowException) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", HttpStatus.FORBIDDEN.value());
            jsonObject.put("msg", "商品：" + goodsID + "fallback");
            return jsonObject.toJSONString();
        }
        return "testParamFlowRuleFallback";
    }


//    public String testSentinelBlockHandler(BlockException e) {
//        e.printStackTrace();
//        return "testSentinel BlockHandler";
//    }
//
//    public String testSentinelFallback() {
//        return "testSentinel fallback";
//    }

    @GetMapping("/getResultByOpenFeign")
    public Map getResultByOpenFeign() {
        String sentinelToken = httpServletRequest.getHeader("sentinelToken");
        System.out.println("sentinelToken:" + sentinelToken);
        return sentinelProviderFeignClient.getResult("朱伟伟");
    }

    @GetMapping("/getResult")
    public Map getResult() {
        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-provider-9005");
//        List<ServiceInstance> instances = discoveryClient.getInstances("nacos-consumer-9006");
        //Access through the combination of LoadBalanceClient and RestTemplate
        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-provider-9005");
//        ServiceInstance serviceInstance = loadBalancerClient.choose("nacos-consumer-9006");
        String path = String.format("http://%s:%s/nacos-provider-9005/provider/%s", serviceInstance.getHost(), serviceInstance.getPort(), "朱伟伟");
        System.out.println("request path:" + path);
        return restTemplate.getForObject(path, Map.class);
    }

}
