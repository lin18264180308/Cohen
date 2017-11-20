package com.mrong.lineview.common.entity;

import java.util.Date;

/**
 * 备注总览类
 * 
 * @author 张裕宝
 */
public class Remark {

    private Integer id;
    // 班组id
    private Integer teamid;
    // 开始时间
    private Date startTime;
    // 结束时间
    private Date stopTime;
    // 停机分类
    private String outageClassification;
    // 一级停机分类
    private String firstClassStopClassification;
    // 二级停机分类
    private String secondClassStopClassification;
    // 故障详细信息
    private String faultDetails;

    public Integer getTeamid() {
        return teamid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
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

    public String getOutageClassification() {
        return outageClassification;
    }

    public void setOutageClassification(String outageClassification) {
        this.outageClassification = outageClassification;
    }

    public String getFirstClassStopClassification() {
        return firstClassStopClassification;
    }

    public void setFirstClassStopClassification(String firstClassStopClassification) {
        this.firstClassStopClassification = firstClassStopClassification;
    }

    public String getSecondClassStopClassification() {
        return secondClassStopClassification;
    }

    public void setSecondClassStopClassification(String secondClassStopClassification) {
        this.secondClassStopClassification = secondClassStopClassification;
    }

    public String getFaultDetails() {
        return faultDetails;
    }

    public void setFaultDetails(String faultDetails) {
        this.faultDetails = faultDetails;
    }

    @Override
    public String toString() {
        return " " + teamid + ", " + startTime + ", " + stopTime + ", " + outageClassification + ", "
                + firstClassStopClassification + ", " + secondClassStopClassification + ", " + faultDetails;
    }

}
