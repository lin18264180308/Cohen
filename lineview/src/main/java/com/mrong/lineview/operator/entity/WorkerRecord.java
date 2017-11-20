package com.mrong.lineview.operator.entity;

import java.util.Date;

/**
 * 工作记录类
 * 
 * @author 张裕宝
 */
public class WorkerRecord {

    private Integer id;
    // 班次id
    private Integer shiftId;
    // 班组id
    private Integer teamId;
    // 时间
    private Date date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShiftId() {
        return shiftId;
    }

    public void setShiftId(Integer shiftId) {
        this.shiftId = shiftId;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
