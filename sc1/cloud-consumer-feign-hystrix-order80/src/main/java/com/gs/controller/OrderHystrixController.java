package com.gs.controller;

import com.gs.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@DefaultProperties(defaultFallback = "globalFallback")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;


    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String payment_OK(@PathVariable("id") Integer id){
        return paymentHystrixService.payment_OK(id);
    }

//    @HystrixCommand(fallbackMethod = "paymentTimeoutFallBackMethod",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
//    })
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand
    public String payment_Timeout(@PathVariable("id") Integer id){
        return paymentHystrixService.payment_Timeout(id);
    }

    public String paymentTimeoutFallBackMethod(@PathVariable("id") Integer id){
        return "系统繁忙";
    }

    //global fallback
    public String globalFallback(){
        return "global 系统繁忙";
    }
}