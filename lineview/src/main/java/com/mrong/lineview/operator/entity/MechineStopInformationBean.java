package com.mrong.lineview.operator.entity;

import java.util.Date;

/**
 * 机台停机信息
 * 
 * @author 林金成
 *         2017年10月18日
 */
public class MechineStopInformationBean {
    /**
     * id
     */
    private Integer id;
    /**
     * 机台名称
     */
    private String mechineName;
    /**
     * 开始时间
     */
    private Date beginTime;
    /**
     * 持续时间
     */
    private Date endTime;

    /**
     * 停机颜色
     */
    private String color;

    public MechineStopInformationBean() {
    }

    public MechineStopInformationBean(Integer id, String mechineName, Date beginTime, Date endTime, String color) {
        this.id = id;
        this.mechineName = mechineName;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.color = color;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMechineName() {
        return mechineName;
    }

    public void setMechineName(String mechineName) {
        this.mechineName = mechineName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
