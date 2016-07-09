package com.itheima.service;

import com.itheima.dao.OrderDao;
import com.itheima.dao.ProdDao;
import com.itheima.dao.UserDao;
import com.itheima.domain.*;
import com.itheima.factory.BasicFactory;
import com.itheima.util.DaoUtils;
import com.itheima.util.TransactionManager;
import org.apache.commons.dbutils.DbUtils;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Meiling on 2016/7/7.
 */
public class OrderServiceImpl implements OrderService {
    OrderDao orderdao = BasicFactory.getFactory().getDao(OrderDao.class);
    ProdDao prodao = BasicFactory.getFactory().getDao(ProdDao.class);
    UserDao userDao = BasicFactory.getFactory().getDao(UserDao.class);

    @Override
    public void addOrder(Order order) {
        //注释掉的是我们之前的方法 但容易造成耦合（conn 是dao层的所以最好不再这用）
        //所以接下来我们用个第三方 调解方  ThreadLocal 解决
        //111
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


        //2 事务管理 在BasicFactory 里面进行
       /* try {
            TransactionManager.startTran();
            //生成订单
            orderdao.addOrder(order);
            // 生成订单项  //扣除商品数量
            for (OrderItem orderItem : order.getList()) {
                orderdao.addOrderItem(orderItem);
                prodao.delPnum(orderItem.getProduct_id(), orderItem.getBuynum());
            }
            TransactionManager.commit();
        } catch (Exception e) {
            TransactionManager.rollBack();
            e.printStackTrace();
            ;
            throw new RuntimeException(e);
        } finally {
            TransactionManager.release();
        }*/
        //事务管理 在BasicFactory 里面进行
        try {
            //生成订单
            orderdao.addOrder(order);
            // 生成订单项  //扣除商品数量
            for (OrderItem orderItem : order.getList()) {
                orderdao.addOrderItem(orderItem);
                prodao.delPnum(orderItem.getProduct_id(), orderItem.getBuynum());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderForm> findOrders(int userid) {
        List<OrderForm> ofList = new ArrayList<>();

        //1根据id 查询所有订单
        List<Order> orderList = orderdao.findOrderByUserId(userid);


        for (Order order : orderList) {
            // 2遍历订单 生成of对象 存入list
            OrderForm of = new OrderForm();
            of.setOrder(order);//设置订单信息
            //设置用户名
            User user = userDao.findUserById(userid);
            of.setUsername(user.getUsername());
            //设置商品列表信息
            //-查询当前订单所有的订单项
            List<OrderItem> orderItemList = orderdao.findOrderItems(order.getId());
            //--遍历所有订单项 获取商品id 查询商品list 存入 of
            Map<Product, Integer> prodmap = new HashMap<>();
            for (OrderItem orderItem : orderItemList) {
                String productId = orderItem.getProduct_id();
                Product product = prodao.findProdById(productId);
                prodmap.put(product, orderItem.getBuynum());
            }
            of.setProdmap(prodmap);
            ofList.add(of);
        }
        return ofList;
    }

    @Override
    public void delOrderById(String orderId) {
        //1.根据id查询出所有订单项
        List<OrderItem> list = orderdao.findOrderItems(orderId);
        //2.将订单项内的商品加回去
        for (OrderItem orderItem : list) {
            prodao.addPnum(orderItem.getProduct_id(),orderItem.getBuynum());
        }
        //3.删除 订单项
        orderdao.delOrderItem(orderId);
        //4.删除订单
        orderdao.delOrder(orderId);
    }
}
