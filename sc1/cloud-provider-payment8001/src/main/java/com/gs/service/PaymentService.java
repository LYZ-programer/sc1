package com.gs.service;

import com.gs.bean.Payment;

public interface PaymentService {
    int add(Payment payment);
    Payment getPaymentById(Long id);
}
