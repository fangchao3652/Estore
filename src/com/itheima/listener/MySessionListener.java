package com.itheima.listener;

import com.itheima.domain.Product;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.LinkedHashMap;

/**
 * Created by Meiling on 2016/7/7.
 */
public class MySessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
httpSessionEvent.getSession().setAttribute("cartmap",new LinkedHashMap<Product,Integer>());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {

    }
}
