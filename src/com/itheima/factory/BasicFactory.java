package com.itheima.factory;

import java.io.FileReader;
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

    public <T> T getInstance(Class<T> clazz) {
        try {
            String infname = clazz.getSimpleName();
            String imoleName = properties.getProperty(infname);
            return (T) Class.forName(imoleName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

    }
}
