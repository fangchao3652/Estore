package com.itheima.web;

import com.itheima.domain.Product;
import com.itheima.factory.BasicFactory;
import com.itheima.service.ProdService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Meiling on 2016/7/7.
 */
@WebServlet(name = "AddcartServlet")
public class AddcartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
//1.根据id找出该商品
        String id = request.getParameter("id");
        Product pro = service.findProdById(id);

        //2 像cartmap添加这个商品 如果之前没有这个上商品 则将数量设为1  如果已经有了就把数量+1
        if (pro == null) {
            throw new RuntimeException("找不到该商品！！");
        } else {
            Map<Product, Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
            cartmap.put(pro, cartmap.containsKey(pro) ? cartmap.get(pro) + 1 : 1);//要重写product bean 的hashcode ...equals 方法
        }
        // 重定向到购物车页面
        response.sendRedirect(request.getContextPath()+"/cart.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
