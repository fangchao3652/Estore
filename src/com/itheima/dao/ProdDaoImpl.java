package com.itheima.dao;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.domain.Product;
import com.itheima.util.DaoUtils;

public class ProdDaoImpl implements ProdDao {

	public void addProd(Product prod) {
		String sql = "insert into products values (?,?,?,?,?,?,?)";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			runner.update(sql,prod.getId(),prod.getName(),prod.getPrice(),prod.getCategory(),prod.getPnum(),prod.getImgurl(),prod.getDescription());
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Product> findAllProd() {
		String sql = "select * from products";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanListHandler<Product>(Product.class));
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Product findProdById(String id) {
		String sql = "select * from products where id = ?";
		try{
			QueryRunner runner = new QueryRunner(DaoUtils.getSource());
			return runner.query(sql, new BeanHandler<Product>(Product.class),id);
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
