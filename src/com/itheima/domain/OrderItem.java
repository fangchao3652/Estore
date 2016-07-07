package com.itheima.domain;

import java.io.Serializable;

/**
 * Created by Meiling on 2016/7/7.
 */
public class OrderItem implements Serializable{
    private String order_id;
    private String product_id;
    private int buynum;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }
}
