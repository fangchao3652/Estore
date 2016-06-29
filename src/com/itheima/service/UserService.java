package com.itheima.service;

import com.itheima.domain.User;

/**
 * Created by Meiling on 2016/6/25.
 */
public interface UserService {
    void regist(User user);

    User getUserByNameAndPsw(String username, String password);
}
