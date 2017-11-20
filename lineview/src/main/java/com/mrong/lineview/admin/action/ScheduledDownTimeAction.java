package com.mrong.lineview.admin.action;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.service.ScheduledDownTimeService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.TimeUtils;

/**
 * 计划停机报表
 * 
 * @author 林金成
 *         2017年11月16日
 */
public class ScheduledDownTimeAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 6488067594649285896L;
    private ScheduledDownTimeService scheduledDownTimeService;

    /**
     * 计划停机
     */
    private List<Entry<String, Double>> scheduledDownTime;

    private String begin;
    private String end;

    /**
     * 因果损失分析报表数据加载方法
     */
    public String loadData() {
        Date startTime = TimeUtils.toDate(begin);
        Date stopTime = TimeUtils.toDate(end);
        /** 计算计划停机 **/
        scheduledDownTime = scheduledDownTimeService.scheduledDownTime(startTime, stopTime);

        return JSON;
    }

    /** ------------------------------getter and setter------------------------------------------ **/

    public List<Entry<String, Double>> getScheduledDownTime() {
        return scheduledDownTime;
    }

    public void setScheduledDownTimeService(ScheduledDownTimeService scheduledDownTimeService) {
        this.scheduledDownTimeService = scheduledDownTimeService;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
