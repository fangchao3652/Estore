package com.itheima.web;

import com.itheima.factory.BasicFactory;
import com.itheima.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Meiling on 2016/7/9.
 */
@WebServlet(name = "DelOrderServlet")
public class DelOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = BasicFactory.getFactory().getService(OrderService.class);
        //1.获取订单id
        String orderId = request.getParameter("id");
        //2.调用service 删除 订单
        orderService.delOrderById(orderId);
        //3.回到订单列表
        request.getRequestDispatcher("/OrderListServlet").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
