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
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Meiling on 2016/7/7.
 */
@WebServlet(name = "ChangeCartServlet")
public class ChangeCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdService service = BasicFactory.getFactory().getInstance(ProdService.class);
        //1.获取要删除的id,根据id查找出商品
        String id = request.getParameter("id");
        Product prod = service.findProdById(id);
        //2.获取购物车,修改数量
        Map<Product,Integer> cartmap = (Map<Product, Integer>) request.getSession().getAttribute("cartmap");
        cartmap.put(prod, Integer.parseInt(request.getParameter("buynum")));
        //3.重定向回到购物车页面
        response.sendRedirect(request.getContextPath()+"/cart.jsp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request, response);
    }

    public static void main(String[] args) {
        Map map=new TreeMap();
        map.put("1",12);
        map.put("1",123);
        System.out.println(map.get("1"));
    }
}
