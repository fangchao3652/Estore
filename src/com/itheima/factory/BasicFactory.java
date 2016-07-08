package com.itheima.factory;

import com.itheima.annotation.Tran;
import com.itheima.dao.Dao;
import com.itheima.service.Service;
import com.itheima.util.TransactionManager;

import java.io.FileReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by Meiling on 2016/6/25.
 */
public class BasicFactory {
    private static BasicFactory factory = new BasicFactory();
    private static Properties properties = null;

    static {
        try {
            properties = new Properties();
            properties.load(new FileReader(BasicFactory.class.getClassLoader().getResource("config.properties").getPath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BasicFactory() {
    }

    public static BasicFactory getFactory() {
        return factory;
    }

    //    public <T> T getInstance(Class<T> clazz) {
//        try {
//            String infname = clazz.getSimpleName();
//            String imoleName = properties.getProperty(infname);
//            return (T) Class.forName(imoleName).newInstance();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }
//
//    }
    public <T extends Dao> T getDao(Class<T> clazz) {
        try {
            String infname = clazz.getSimpleName();
            String imoleName = properties.getProperty(infname);
            return (T) Class.forName(imoleName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }


    public <T extends Service> T getService(Class<T> clazz) {
        try {
            String infname = clazz.getSimpleName();
            String imoleName = properties.getProperty(infname);

            final T service = (T) Class.forName(imoleName).newInstance();
            //对他进行代理
            T proxyService = (T) Proxy.newProxyInstance(service.getClass().getClassLoader(), service.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                   if(method.isAnnotationPresent(Tran.class)){
                       //有注解  才开启事务
                       try {
                           //原方法执行之前开始事务
                           TransactionManager.startTran();

                           Object obj = method.invoke(service, args);
                           //之后提交事务
                           TransactionManager.commit();
                           return obj;
                       } catch (InvocationTargetException e) {//底层方法调用抛出的异常
                           TransactionManager.rollBack();
                           throw new RuntimeException(e.getTargetException());//未代理的异常
                       } catch (Exception e) {
                           TransactionManager.rollBack();
                           e.printStackTrace();
                           throw new RuntimeException(e);
                       } finally {
                           TransactionManager.release();
                       }
                   }else{//没有注解
                       return method.invoke(service, args);
                   }

                }
            });
            return proxyService;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
