package com.itheima.service;

import java.util.List;

import com.itheima.domain.Product;

public interface ProdService {

	/**
	 * ??????
	 * @param prod ??????bean
	 */
	void addProd(Product prod);

	/**
	 * ??????????
	 * @return
	 */
	List<Product> findAllProd();

	/**
	 * ????id??????????
	 * @param id
	 * @return
	 */
	Product findProdById(String id);

}
