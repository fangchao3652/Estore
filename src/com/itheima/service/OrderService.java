package com.itheima.service;

import com.itheima.annotation.Tran;
import com.itheima.domain.Order;
import com.itheima.domain.OrderForm;

import java.util.List;

/**
 * Created by Meiling on 2016/7/7.
 */
public interface OrderService extends Service {
    /**
     * 增加生成订单
     *
     * @param order
     */
    @Tran
    void addOrder(Order order);

    /**
     * 查询 指定用户的所有的订单
     *
     * @param userid
     */
    List<OrderForm> findOrders(int userid);
}
