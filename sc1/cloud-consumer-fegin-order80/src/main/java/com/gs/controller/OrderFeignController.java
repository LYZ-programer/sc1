package com.gs.controller;

import com.gs.bean.CommonResult;
import com.gs.bean.Payment;
import com.gs.service.PaymentFeignService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> add(@PathVariable("id") Long id){
        return paymentFeignService.add(id);
    }

    @GetMapping("/consumer/payment/feign/timeout")
    public String paymentFeignTimeout(){
        //openFeign集成ribbon，客户端默认等待1秒钟
        return paymentFeignTimeout();
    }
}