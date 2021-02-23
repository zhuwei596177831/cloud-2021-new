package com.cloud.nacos.learning.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 朱伟伟
 * @date 2021-02-21 11:57:30
 * @description
 */
@RestController
public class TimeoutController {

    @GetMapping("/openFeignTimeout")
    public String openFeignTimeout() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "openFeignTimeout";
    }

}
