package com.itheima.dao;

import com.itheima.domain.User;

/**
 * Created by Meiling on 2016/6/25.
 */
public interface UserDao extends Dao {
    Object findUserByName(String username);

    void addUser(User user);

    User finUserByNameAndPsw(String username, String password);
}
