package com.itheima.dao;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.util.DaoUtils;
import com.itheima.util.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by Meiling on 2016/7/7.
 */
public class OrderDaoImpl implements OrderDao {
    @Override
    public void addOrder(Order order) {
        String sql = "insert into orders values (?,?,?,?,null,?)";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            queryRunner.update(sql, order.getId(), order.getMoney(), order.getReceiverinfo(), order.getPaystate(), order.getUser_id());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql = "insert into orderitem values (?,?,?)";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            queryRunner.update(sql, orderItem.getOrder_id(), orderItem.getProduct_id(), orderItem.getBuynum());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findOrderByUserId(int userid) {
        String sql = "select * from orders where user_id=?";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            return queryRunner.query(sql, new BeanListHandler<Order>(Order.class), userid);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<OrderItem> findOrderItems(String id) {
        String sql = "select * from  orderitem where order_id=?";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            return queryRunner.query(sql, new BeanListHandler<OrderItem>(OrderItem.class), id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delOrderItem(String orderId) {
        String sql = "delete from orderitem where order_id=?";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            queryRunner.update(sql, orderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delOrder(String orderId) {
        String sql = "delete from orders  where  id=?";
        try {
            QueryRunner queryRunner = new QueryRunner(TransactionManager.getSource());
            queryRunner.update(sql, orderId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
