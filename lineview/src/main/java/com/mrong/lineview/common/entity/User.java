package com.mrong.lineview.common.entity;

import java.util.Date;

/**
 * 用户类，包含操作工及管理员
 * 
 * @author 张裕宝
 */
public class User {

    private Integer id;
    // 用户姓名
    private String userName;
    // 用户密码
    private String userPassword;
    // 用户权限
    private String permission;
    // 修改时间
    private Date updataTime;
    // 修改人员
    private String updatePerson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Date getUpdataTime() {
        return updataTime;
    }

    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }

    public String getUpdatePerson() {
        return updatePerson;
    }

    public void setUpdatePerson(String updatePerson) {
        this.updatePerson = updatePerson;
    }

}
