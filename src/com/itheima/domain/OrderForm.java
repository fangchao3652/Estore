package com.itheima.domain;

import java.util.List;

/**
 * 订单查询的时候 一些数据Order bean中没有所以再建一个
 * Created by Fang on 2016/7/9.
 */
public class OrderForm{
    private Order order;
    private String username;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    private List<Product> productList;
}
