package com.gs.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    /**
     * 正常访问
     * @param id
     * @return
     */
    public String payment_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+"payment_OK,id"+id;
    }

    @HystrixCommand(fallbackMethod = "payment_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String payment_Timeout(Integer id){
        int second = 3;
        try{
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int age = 10/0;
        return "线程池："+Thread.currentThread().getName()+"payment_Timeout,id"+id+"\t"+"耗时"+second+"秒";
    }

    public String payment_TimeoutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"payment_TimeoutHandler,id"+id;
    }

    //====服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")//失败率到多少熔断
    })
    public String paymentCircuitBreaker(Integer id){
        if (id<0){
            throw new RuntimeException("id不能是负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，流水号："+serialNumber;
    }

    public String paymentCircuitBreaker_fallback(Integer id){
        return "id不能是负数,id：" + id;
    }
}