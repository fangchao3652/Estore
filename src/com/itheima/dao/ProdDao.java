package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Product;

public interface ProdDao extends Dao {

    /**
     * @param prod
     */
    void addProd(Product prod);

    /**
     * @return
     */
    List<Product> findAllProd();

    /**
     * @param id
     * @return
     */
    Product findProdById(String id);

    /**
     * 减去 产品数量
     *
     * @param product_id
     * @param buynum
     */
    void delPnum(String product_id, int buynum);

    /**
     * 加回产品数量
     *
     * @param product_id
     * @param buynum
     */
    void addPnum(String product_id, int buynum);
}
