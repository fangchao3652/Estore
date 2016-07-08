package com.itheima.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ProxyFactory;

import javax.sql.DataSource;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Meiling on 2016/7/8.
 */
public class TransactionManager {
    private static DataSource source = new ComboPooledDataSource();

    //1这种方法 用的是同一个conn所以 多线程的时候会出问题  第一个commit时第二个还没执行完也会被提交//
    //所以用ThreadLocal
    //2  但是每个里都开关事务比较麻烦  我们希望在所有方法开始前执行事务 在所有方法结束后 关事务 所以这里我们用动态代理（在basicFactory中）
    private TransactionManager() {
    }

    //private static Connection conn = DaoUtils.getConn();
    private static ThreadLocal<Connection> conn_ThreadLocal = new ThreadLocal<Connection>() {
       /* @Override
        protected Connection initialValue() {

            return DaoUtils.getConn();
        }*/
    };
    /**
     * 保存每个线程是否开启过事务
     */
    private static ThreadLocal<Boolean> flag_local = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    public static void startTran() throws SQLException {
        // conn.setAutoCommit(false);
        // conn_ThreadLocal.get().setAutoCommit(false);
        flag_local.set(true);
    }

    public static void commit() {
        //DbUtils.commitAndCloseQuietly(conn);
        DbUtils.commitAndCloseQuietly(conn_ThreadLocal.get());
    }

    public static void rollBack() {
        DbUtils.rollbackAndCloseQuietly(conn_ThreadLocal.get());
        //DbUtils.rollbackAndCloseQuietly(conn);
    }

   /* public static Connection getConn() {
        return conn_ThreadLocal.get();
    }*/

    /**
     * 如果没有开启事务拿到的是普通的数据源
     * 如果开启过事务 则返回一个改造过getConnection方法的数据源，这个方法改造后都返回同一个开启过事务的connection
     *
     * @return
     */
    public static DataSource getSource() throws SQLException {
        if (flag_local.get()) {//开启事务了 返回改造的source
            final Connection conn = source.getConnection();
            conn.setAutoCommit(false);
            conn_ThreadLocal.set(conn);
            DataSource proxysource = (DataSource) Proxy.newProxyInstance(source.getClass().getClassLoader(), source.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ("getConnection".equals(method.getName())) {

                        return conn_ThreadLocal.get();
                    } else {
                        return method.invoke(source, args);
                    }
                }
            });
            return proxysource;
        } else {  //没有开启事务 返回普通的数据源
            return source;
        }
    }

    public static void release() {
        conn_ThreadLocal.remove();
    }
}
