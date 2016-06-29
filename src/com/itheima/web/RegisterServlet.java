package com.itheima.web;

import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.service.UserService;
import com.itheima.util.MD5Utils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Meiling on 2016/6/29.
 */
@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService service = BasicFactory.getFactory().getInstance(UserService.class);
        try {
            //1.校验验证码
            String valistr1 = request.getParameter("valistr");
            String valistr2 = (String) request.getSession().getAttribute("valistr");
            if (valistr1 == null || valistr2 == null || !valistr1.equals(valistr2)) {
                request.setAttribute("msg", "<font color='red'>验证码不正确!</font>");
                request.getRequestDispatcher("/regist.jsp").forward(request, response);
                return;
            }

//2.封装数据*校验数据

            User user = new User();
            BeanUtils.populate(user, request.getParameterMap());
            user.setPassword(MD5Utils.md5(user.getPassword()));
            //3.注册用户 调用service
            service.regist(user);
            //4.回到主页
            response.getWriter().write("注册成功,请到邮箱中进行激活...");
            response.setHeader("Refresh", "3;url="+request.getContextPath()+"/index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
doPost(request, response);
    }
}
