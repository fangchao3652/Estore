package com.itheima.dao;

import com.itheima.domain.User;

import java.sql.Connection;

/**
 * Created by Meiling on 2016/6/25.
 */
public interface UserDao {
    Object findUserByName(String username, Connection conn);

    void addUser(User user, Connection conn);

    User finUserByNameAndPsw(String username, String password);
}
