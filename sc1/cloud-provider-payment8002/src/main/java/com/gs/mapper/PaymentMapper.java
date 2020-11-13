package com.gs.mapper;

import com.gs.bean.Payment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface PaymentMapper {
    @Insert("insert into payment(serial) values(#{serial})")
    int add(Payment payment);
    @Select("select * from payment where id=#{id}")
    Payment getPaymentById(@Param("id") Long id);
}
