package com.mrong.lineview.common.entity;

import org.springframework.stereotype.Component;
@SuppressWarnings("all")
public class TimeBean {
    private double totalTime;//期间总时间（TT） // 计划生产
    private double notScheduledTime;//非计划时间（NST） // 非计划生产
    private double downTimeOfPlannedActivities;//计划停机时间（DPA）// 计划停机
    private double externalCaused;//外部原因（EC）// 外部原因
    private double internalCause;//内部原因（IC）// 内部原因

    /**
     * @param totalTime
     *            : 期间总时间（TT）// 计划生产
     * @param scheduledTime
     *            : 计划时间（ST）//
     * @param notScheduledTime
     *            : 非计划时间（NST）
     * @param loadingTime
     *            : 线占用时间（LT）
     * @param downTimeOfPlannedActivities
     *            : 计划停机时间（DPA）
     * @param lineEfficiencyTime
     *            : 线有效运转时间（LET）
     * @param externalCaused
     *            : 外部原因（EC）
     * @param effectiveProductiveTime
     *            : 有效生产时间（EPT）
     * @param internalCause
     *            : 内部原因（IC）
     */
    public TimeBean(double totalTime, double scheduledTime, double notScheduledTime, double loadingTime,
            double downTimeOfPlannedActivities, double lineEfficiencyTime, double externalCaused,
            double effectiveProductiveTime, double internalCause) {
        this.totalTime = totalTime;
        this.notScheduledTime = notScheduledTime;
        this.downTimeOfPlannedActivities = downTimeOfPlannedActivities;
        this.externalCaused = externalCaused;
        this.internalCause = internalCause;
    }

    /**
     * @param totalTime
     *            : 期间总时间（TT）
     * @param downTimeOfPlannedActivities
     *            : 计划停机时间（DPA）
     * @param externalCaused
     *            : 外部原因（EC）
     * @param internalCause
     *            : 内部原因（IC）
     */
    public TimeBean(double totalTime, double downTimeOfPlannedActivities, double externalCaused, double internalCause) {
        this.totalTime = totalTime;
        this.downTimeOfPlannedActivities = downTimeOfPlannedActivities;
        this.externalCaused = externalCaused;
        this.internalCause = internalCause;
    }

    /**
     * 获取总生产时间
     */
    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    /**
     * 获取计划时间
     */
    public double getScheduledTime() {
        return totalTime - notScheduledTime;
    }

    public void setScheduledTime(double scheduledTime) {
    }

    /**
     * 获取非计划时间
     */
    public double getNotScheduledTime() {
        return notScheduledTime;
    }

    public void setNotScheduledTime(double notScheduledTime) {
        this.notScheduledTime = notScheduledTime;
    }

    /**
     * 获取线占用时间
     */
    public double getLoadingTime() {
        return getScheduledTime() - downTimeOfPlannedActivities;
    }

    public void setLoadingTime(double loadingTime) {
    }

    /**
     * 获取计划停机时间
     */
    public double getDownTimeOfPlannedActivities() {
        return downTimeOfPlannedActivities;
    }

    public void setDownTimeOfPlannedActivities(double downTimeOfPlannedActivities) {
        this.downTimeOfPlannedActivities = downTimeOfPlannedActivities;
    }

    /**
     * 获取线有效运转时间
     */
    public double getLineEfficiencyTime() {
        return getLoadingTime() - externalCaused;
    }

    public void setLineEfficiencyTime(double lineEfficiencyTime) {
    }

    /**
     * 获取外部原因
     */
    public double getExternalCaused() {
        return externalCaused;
    }

    public void setExternalCaused(double externalCaused) {
        this.externalCaused = externalCaused;
    }

    /**
     * 获取有效生产时间
     */
    public double getEffectiveProductiveTime() {
        return getLineEfficiencyTime() - internalCause;
    }

    public void setEffectiveProductiveTime(double effectiveProductiveTime) {
    }

    /**
     * 获取内部原因
     */
    public double getInternalCause() {
        return internalCause;
    }

    public void setInternalCause(double internalCause) {
        this.internalCause = internalCause;
    }

    public TimeBean() {
    }
}
