package com.mrong.lineview.common.entity;

import java.util.Date;

/**
 * 各机台停机故障信息一览类
 * 
 * @author 张裕宝
 */
public class HaltBean {

    private Integer id;
    // 机台名称
    private String machineName;
    // 停机分类
    private String outageClassification;
    // 停机次数(只能是一次)
    private String showdownTimers;
    // 开始时间
    private Date startTime;
    // 停止时间
    private Date stopTime;
    // 持续时间
    private String duration;

    public Integer getId() {
        return id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOutageClassification() {
        return outageClassification;
    }

    public void setOutageClassification(String outageClassification) {
        this.outageClassification = outageClassification;
    }

    public String getShowdownTimers() {
        return showdownTimers;
    }

    public void setShowdownTimers(String showdownTimers) {
        this.showdownTimers = showdownTimers;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
