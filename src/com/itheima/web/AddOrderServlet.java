package com.itheima.web;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;
import com.itheima.service.OrderServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Meiling on 2016/7/7.
 */
@WebServlet(name = "AddOrderServlet")
public class AddOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService service = BasicFactory.getFactory().getInstance(OrderService.class);
        try {
            //1.将订单信息存入Order bean 中
            Order order = new Order();
            //订单编号
            order.setId(UUID.randomUUID().toString());
            //收货地址
            BeanUtils.populate(order, request.getParameterMap());
            //金额     //将订单项存入order中
            Map<Product, Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
            double money = 0;
            List<OrderItem> list = new ArrayList<>();
            for (Map.Entry<Product, Integer> entry : cartmap.entrySet()) {
                money += entry.getKey().getPrice() * entry.getValue();
                OrderItem orderItem = new OrderItem();
                orderItem.setBuynum(entry.getValue());
                orderItem.setOrder_id(order.getId());
                orderItem.setProduct_id(entry.getKey().getId());
                list.add(orderItem);
            }
            order.setMoney(money);
            //客户编号
            order.setUser_id(((User) request.getSession().getAttribute("user")).getId());

            //订单项
            order.setList(list);
            //支付状态
            order.setPaystate(0);
            //2.调用Service中的方法添加订单的方法
            service.addOrder(order);
            //3.清空够购物车
            cartmap.clear();
            //4.回到主页
            response.getWriter().write("订单生成成功，请去支付！");
            response.setHeader("refresh", "3;url=" + request.getContextPath() + "/index.jsp");

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
