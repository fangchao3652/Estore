package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Product;

public interface ProdDao {

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

}