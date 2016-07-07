package com.itheima.dao;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.util.DaoUtils;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Created by Meiling on 2016/7/7.
 */
public class OrderDaoImpl implements  OrderDao{
    @Override
    public void addOrder(Order order) {
        String sql="insert into orders values (?,?,?,?,null,?)";
        try {
            QueryRunner queryRunner=new QueryRunner(DaoUtils.getSource());
            queryRunner.update(sql,order.getId(),order.getMoney(),order.getReceiverinfo(),order.getPaystate(),order.getUser_id());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addOrderItem(OrderItem orderItem) {
        String sql="insert into orderitem values (?,?,?)";
        try {
            QueryRunner queryRunner=new QueryRunner(DaoUtils.getSource());
            queryRunner.update(sql,orderItem.getOrder_id(),orderItem.getProduct_id(),orderItem.getBuynum());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
