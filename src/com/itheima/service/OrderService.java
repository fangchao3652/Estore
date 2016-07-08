package com.itheima.service;

import com.itheima.annotation.Tran;
import com.itheima.domain.Order;

/**
 * Created by Meiling on 2016/7/7.
 */
public interface OrderService extends Service{
    @Tran
    void addOrder(Order order);
}
