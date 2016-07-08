package com.itheima.dao;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;

/**
 * Created by Meiling on 2016/7/7.
 */
public interface OrderDao  extends Dao{
    /**
     * 在订单表中插入记录
     * @param order
     */
    void addOrder(Order order);

    /**
     * 在订单项 表中插入记录
     * @param orderItem
     */
    void addOrderItem(OrderItem orderItem);
}
