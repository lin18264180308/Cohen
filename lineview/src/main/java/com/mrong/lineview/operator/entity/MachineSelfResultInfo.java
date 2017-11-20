package com.mrong.lineview.operator.entity;

import java.util.Date;

/**
 * 机台停机具体故障信息
 * 
 * @author 林金成
 *         2017年10月27日
 */
public class MachineSelfResultInfo {

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 机台名称
     */
    private String machineName;

    /**
     * 故障名称
     */
    private String resultInfo;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 当前时间
     */
    private Date currentTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
    }

    public MachineSelfResultInfo(Integer id, String machineName, String resultInfo, Date startTime, Date endTime,
            Date currentTime) {
        this.id = id;
        this.machineName = machineName;
        this.resultInfo = resultInfo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentTime = currentTime;
    }

    public MachineSelfResultInfo() {
    }

    @Override
    public String toString() {
        return "MachineSelfResultInfo [id=" + id + ", machineName=" + machineName + ", resultInfo=" + resultInfo
                + ", startTime=" + startTime + ", endTime=" + endTime + ", currentTime=" + currentTime + "]";
    }
}
