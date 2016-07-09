package com.itheima.dao;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;

import java.util.List;

/**
 * Created by Meiling on 2016/7/7.
 */
public interface OrderDao extends Dao {
    /**
     * 在订单表中插入记录
     *
     * @param order
     */
    void addOrder(Order order);

    /**
     * 在订单项 表中插入记录
     *
     * @param orderItem
     */
    void addOrderItem(OrderItem orderItem);

    /**
     * 查询指定用户的所有的订单
     *
     * @param userid
     * @return
     */
    List<Order> findOrderByUserId(int userid);

    /**
     * 查询当前订单所有的订单项
     *
     * @param id
     * @return
     */
    List<OrderItem> findOrderItems(String id);

    /**
     * 根据订单号 删除该订单所关联的订单项
     *
     * @param orderId
     */
    void delOrderItem(String orderId);

    /**
     * 删除指定的订单
     *
     * @param orderId
     */
    void delOrder(String orderId);
}
