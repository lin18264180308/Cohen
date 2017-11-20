package com.mrong.lineview.admin.service;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.entity.Entry;

public interface ScheduledDownTimeService {
    /**
     * 计划停机状态统计
     * (计划停机时间/计划生产时间)*(1-GLY)
     */
    public List<Entry<String, Double>> scheduledDownTime(Date startTime, Date stopTime);
}
