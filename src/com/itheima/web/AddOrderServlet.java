package com.itheima.web;

import com.itheima.domain.Order;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Meiling on 2016/7/7.
 */
@WebServlet(name = "AddOrderServlet")
public class AddOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //1.将订单信息存入Order bean 中
            Order order = new Order();
            BeanUtils.populate(order, request.getParameterMap());
            Map<Product, Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
            double money = 0;
            for (Map.Entry<Product, Integer> entry : cartmap.entrySet()) {
                money += entry.getKey().getPrice() * entry.getValue();
            }
            order.setMoney(money);
            order.setUser_id(((User)request.getSession().getAttribute("user")).getId());

            //2.调用Service中的方法添加订单的方法

            //3.清空够购物车
            //4.回到主页
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
