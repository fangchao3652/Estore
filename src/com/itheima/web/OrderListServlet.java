package com.itheima.web;

import com.itheima.domain.Order;
import com.itheima.domain.OrderForm;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Fang on 2016/7/9.
 */
@WebServlet(name = "OrderListServlet")
public class OrderListServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService service = BasicFactory.getFactory().getService(OrderService.class);

        //1.获取用户编号 用户id
        User user = (User) request.getSession().getAttribute("user");
        int id = user.getId();
        //2. 调用service 查询用户的订单
        List<OrderForm> list = service.findOrders(id);
        //3.通过request域 带到jsp展示
        request.setAttribute("list", list);
        request.getRequestDispatcher("/orderList.jsp").forward(request, response);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
