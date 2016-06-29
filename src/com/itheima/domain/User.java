package com.itheima.domain;


import java.sql.Timestamp;

/**
 * Created by Meiling on 2016/6/29.
 */
public class User {
    private int id;
    private  String  username;
    private  String  password;
    private  String  role;
    private  String  nickname;
    private Timestamp updatetime;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private  String email;
    private  String  activecode;
    private  int  state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getActivecode() {
        return activecode;
    }

    public void setActivecode(String activecode) {
        this.activecode = activecode;
    }

    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }



}
