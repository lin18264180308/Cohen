package com.mrong.lineview.admin.service;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.entity.PerformanceIndicators;

/**
 * 绩效曲线报表service
 * 
 * @author 林金成
 *         2017年11月17日
 */
public interface PerformanceCurveService {

    /**
     * 获取绩效曲线报表数据
     * 每天的OEE/GLY/LEF/总瓶数
     * 
     * @return
     */
    public List<PerformanceIndicators> getPerformanceCurveOfDays(Date startTime, Date stopTime);

    /**
     * 获取绩效曲线报表数据
     * 时间范围内的OEE/GLY/LEF/总瓶数
     * 
     * @return
     */
    public PerformanceIndicators getPerformanceCurve(Date startTime, Date stopTime);

    /**
     * 计算保存当前天的绩效指标
     */
    public String savePerformanceIndicatorsOfCurrentDay(Date startTime, Date stopTime);
}
