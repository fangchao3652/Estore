package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.util.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.domain.Product;
import com.itheima.util.DaoUtils;

public class ProdDaoImpl implements ProdDao {

    public void addProd(Product prod) {
        String sql = "insert into products values (?,?,?,?,?,?,?)";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql, prod.getId(), prod.getName(), prod.getPrice(), prod.getCategory(), prod.getPnum(), prod.getImgurl(), prod.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Product> findAllProd() {
        String sql = "select * from products";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanListHandler<Product>(Product.class));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public Product findProdById(String id) {
        String sql = "select * from products where id = ?";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanHandler<Product>(Product.class), id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //结算的时候 减去商品的数量
    @Override
    public void delPnum(String product_id, int buynum) {
        String sql = " update products set pnum=pnum-? where id=? and pnum-?>=0";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            int count = runner.update(sql, buynum, product_id, buynum);
            if (count <= 0) {
                throw new SQLException("库存不足！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPnum(String product_id, int buynum) {
        String sql = " update products set pnum=pnum+? where id=? ";
        try {
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql, buynum, product_id );

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
