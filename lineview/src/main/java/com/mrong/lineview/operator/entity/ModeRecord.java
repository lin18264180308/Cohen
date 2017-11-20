package com.mrong.lineview.operator.entity;

import java.util.Date;

/**
 * 操作工模式切换记录类
 * 
 * @author 张裕宝
 */
public class ModeRecord {

    private Integer id;
    // 操作工
    private String operator;
    // 模式名称
    private String modeName;
    // 品种
    private String modeClass;
    // 模式类别
    private String patternClasses;
    // 开始时间
    private Date startTime;
    // 结束时间
    private Date stopTime;
    // 持续时间
    private long duration;

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public String getPatternClasses() {
        return patternClasses;
    }

    public void setPatternClasses(String patternClasses) {
        this.patternClasses = patternClasses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getModeClass() {
        return modeClass;
    }

    public void setModeClass(String modeClass) {
        this.modeClass = modeClass;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return " " + id + ", " + operator + ", " + modeName + ", " + modeClass + ", " + patternClasses + ", "
                + startTime + ", " + duration;
    }

}
