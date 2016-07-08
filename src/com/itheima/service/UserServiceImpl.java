package com.itheima.service;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.factory.BasicFactory;
import com.itheima.util.DaoUtils;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;


/**
 * Created by Meiling on 2016/6/25.
 */
public class UserServiceImpl implements UserService {
    private UserDao dao = BasicFactory.getFactory().getDao(UserDao.class);

    @Override
    public void regist(User user) {

        //1.校验用户名是否已经存在
        if (dao.findUserByName(user.getUsername() ) != null) {
            throw new RuntimeException("用户名已经存在!!");
        }
        //2.调用dao中的方法添加用户到数据库
        user.setRole("user");
        user.setState(0);
        user.setActivecode(UUID.randomUUID().toString());
        dao.addUser(user );


    }

    @Override
    public User getUserByNameAndPsw(String username, String password) {
        return dao.finUserByNameAndPsw(username,password);
    }
}
