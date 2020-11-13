package com.gs.service;

import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String payment_OK(Integer id) {
        return "payment_OK fallback";
    }

    @Override
    public String payment_Timeout(Integer id) {
        return "payment_Timeout fallback";
    }
}