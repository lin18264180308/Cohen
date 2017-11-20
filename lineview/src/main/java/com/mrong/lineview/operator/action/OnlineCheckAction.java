package com.mrong.lineview.operator.action;

import com.mrong.lineview.operator.entity.OnlineCheck;
import com.mrong.lineview.operator.service.OnlineCheckService;

/**
 * 线上检查表action
 * 
 * @author 张裕宝
 */
public class OnlineCheckAction {

    private OnlineCheck onlineCheck;
    private String hFan;
    private OnlineCheckService onlineCheckService;
    private Object[] objects;

    public Object[] getObjects() {
        return objects;
    }

    public void setObjects(Object[] objects) {
        this.objects = objects;
    }

    public String gethFan() {
        return hFan;
    }

    public void sethFan(String hFan) {
        this.hFan = hFan;
    }

    public OnlineCheck getOnlineCheck() {
        return onlineCheck;
    }

    public void setOnlineCheck(OnlineCheck onlineCheck) {
        this.onlineCheck = onlineCheck;
    }

    public void setOnlineCheckService(OnlineCheckService onlineCheckService) {
        this.onlineCheckService = onlineCheckService;
    }

    public String onlineCheck() {
        onlineCheckService.saveOnlineCheck(onlineCheck);
        return "success";
    }

    public String getStatus() {
        objects = onlineCheckService.getStatus();
        return "success";
    }
}
