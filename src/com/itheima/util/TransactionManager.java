package com.itheima.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

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

    private static ThreadLocal<Connection> realConn_ThreadLocal = new ThreadLocal<Connection>();
    /**
     * 保存每个线程是否开启过事务
     */
    private static ThreadLocal<Boolean> flag_ThreadLocal = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    public static void startTran() throws SQLException {
        // conn.setAutoCommit(false);
        // conn_ThreadLocal.get().setAutoCommit(false);
        flag_ThreadLocal.set(true);

        final Connection conn = source.getConnection();
        conn.setAutoCommit(false);
       // System.out.println("autocommit====================="+conn.getAutoCommit());//false
        realConn_ThreadLocal.set(conn);

        //conn 还要进行一次代理 因为queryrunner底层每执行完一次后 都会关连接  我们的事务又都是基于这一个连接的 前面的把conn关闭后 后面的就不能用这个连接了
        //所以改造成 开启事务时调用close方法不会真正的关闭，但事务结束还得关，所以再创建一个realConn_ThreadLocal 用于在release时关连接
        Connection proxConn = (Connection) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ("close".equals(method.getName())) {
                    return null;
                } else {
                    return method.invoke(conn, args);
                }

            }
        });

        //System.out.println("proxy autocommit==== ================"+proxConn.getAutoCommit());//false  被代理者之前调用的可以影响代理者

        conn_ThreadLocal.set(proxConn);
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
        if (flag_ThreadLocal.get()) {//开启事务了 返回改造的source

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
        try {
            realConn_ThreadLocal.get().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        realConn_ThreadLocal.remove();
        conn_ThreadLocal.remove();
        flag_ThreadLocal.remove();

    }

}
