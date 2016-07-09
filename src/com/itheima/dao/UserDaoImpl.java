package com.itheima.dao;

import com.itheima.domain.User;
import com.itheima.util.DaoUtils;
import com.itheima.util.TransactionManager;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by Meiling on 2016/6/25.
 */
public class UserDaoImpl implements UserDao {/*
//这是之前的代码
    @Override
    public User findUserByName(String username) {
        try {
            String sql = "select * from users where username=?";
            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
            return runner.query(TransactionManager.getConn(), sql, new BeanHandler<User>(User.class), username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void addUser(User user) {
        String sql = "insert into users values(null,?,?,?,?,?,?,?,null)";
        try{
            QueryRunner runner = new QueryRunner();
            runner.update(TransactionManager.getConn(),sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getActivecode());
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
//    public void delUser(int id) {
//        String sql = "delete from users where id = ?";
//        try{
//            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
//            runner.update(sql,id);
//        }catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public User findUserByActivecode(String activecode) {
        String sql = "select * from users where activecode = ?";
        try{
            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
            return runner.query(sql, new BeanHandler<User>(User.class),activecode);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateState(int id, int state) {
        String sql = "update users set state = ? where id=?";
        try{
            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
            runner.update(sql,state,id);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public User finUserByNameAndPsw(String username, String password) {
        String sql = "select * from users where username = ? and password = ?";
        try{
            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
            return runner.query(sql, new BeanHandler<User>(User.class) ,username ,password);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }*/

    @Override
    public User findUserByName(String username) {
        try {
            String sql = "select * from users where username=?";
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query( sql, new BeanHandler<User>(User.class), username);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    public void addUser(User user) {
        String sql = "insert into users values(null,?,?,?,?,?,?,?,null)";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update( sql,user.getUsername(),user.getPassword(),user.getNickname(),user.getEmail(),user.getRole(),user.getState(),user.getActivecode());
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
//    public void delUser(int id) {
//        String sql = "delete from users where id = ?";
//        try{
//            QueryRunner runner = new QueryRunner(DaoUtils.getSource());
//            runner.update(sql,id);
//        }catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }

    public User findUserByActivecode(String activecode) {
        String sql = "select * from users where activecode = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanHandler<User>(User.class),activecode);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void updateState(int id, int state) {
        String sql = "update users set state = ? where id=?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            runner.update(sql,state,id);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public User finUserByNameAndPsw(String username, String password) {
        String sql = "select * from users where username = ? and password = ?";
        try{
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query(sql, new BeanHandler<User>(User.class) ,username ,password);
        }catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public User findUserById(int userid) {
        try {
            String sql = "select * from users where id=?";
            QueryRunner runner = new QueryRunner(TransactionManager.getSource());
            return runner.query( sql, new BeanHandler<User>(User.class), userid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}

