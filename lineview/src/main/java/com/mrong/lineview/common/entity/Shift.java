package com.mrong.lineview.common.entity;

import java.util.Date;

/**
 * 班次类
 * 
 * @author 张裕宝
 */
@SuppressWarnings("all")
public class Shift {

    public Integer id;
    // 开始时间
    public String startTime;
    // 结束时间
    public String stopTime;
    // 早班中班晚班描述
    public String shiftDescribe;

    public String getShiftDescribe() {
        return shiftDescribe;
    }

    public void setShiftDescribe(String shiftDescribe) {
        this.shiftDescribe = shiftDescribe;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

}
