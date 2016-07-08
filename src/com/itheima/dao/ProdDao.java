package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Product;

public interface ProdDao extends Dao{

	/**
	 * @param prod
	 */
	void addProd(Product prod);

	/**

	 * @return
	 */
	List<Product> findAllProd();

	/**
	 *
	 * @param id
	 * @return
	 */
	Product findProdById(String id);

	void delPnum(String product_id, int buynum);
}
