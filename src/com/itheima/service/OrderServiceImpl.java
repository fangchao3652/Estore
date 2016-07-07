package com.itheima.service;

import com.itheima.dao.OrderDao;
import com.itheima.dao.ProdDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.factory.BasicFactory;
import com.itheima.util.DaoUtils;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by Meiling on 2016/7/7.
 */
public class OrderServiceImpl implements OrderService {
    OrderDao orderdao = BasicFactory.getFactory().getInstance(OrderDao.class);
    ProdDao prodao = BasicFactory.getFactory().getInstance(ProdDao.class);

    @Override
    public void addOrder(Order order) {
        //注释掉的是我们之前的方法 但容易造成耦合（conn 是dao层的所以最好不再这用）
        //所以接下来我们用个第三方 调解方  ThreadLocal 解决
//        Connection connection = DaoUtils.getConn();
//        try {
//            connection.setAutoCommit(false); //事务要在下面把connection传进去  但容易造成耦合（conn 是dao层的所以最好不再这用）
//
//            //生成订单
//            orderdao.addOrder(connection, order);
//            // 生成订单项  //扣除商品数量
//            for (OrderItem orderItem : order.getList()) {
//                orderdao.addOrderItem(connection, orderItem);
//                prodao.delPnum(connection, orderItem.getProduct_id(), orderItem.getBuynum());
//            }
//
//            DbUtils.commitAndCloseQuietly(connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            DbUtils.rollbackAndCloseQuietly(connection);
//            throw new RuntimeException(e);
//        }


     }
}
