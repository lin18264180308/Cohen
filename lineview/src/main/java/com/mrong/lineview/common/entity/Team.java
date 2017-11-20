package com.mrong.lineview.common.entity;

import com.mrong.lineview.common.entity.User;

/**
 * 班组类
 * 
 * @author 张裕宝
 */
public class Team {

    public Integer id;
    public User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
