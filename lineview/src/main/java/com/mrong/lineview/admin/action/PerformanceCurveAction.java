package com.mrong.lineview.admin.action;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.entity.PerformanceIndicators;
import com.mrong.lineview.admin.service.PerformanceCurveService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.util.TimeUtils;

/**
 * 绩效曲线报表
 * 时间范围内总产量、三大绩效
 * 时间范围内每天的总产量、三大绩效
 * 
 * @author 林金成
 *         2017年11月17日
 */
public class PerformanceCurveAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4158453285687260119L;

    /**
     * service
     */
    private PerformanceCurveService performanceCurveService;

    /**
     * 时间参数
     */
    private String begin;
    private String end;

    /**
     * 总的三大指标和产量
     */
    private PerformanceIndicators performanceCurve;

    /**
     * 时间范围内每天的三大指标与产量
     */
    private List<PerformanceIndicators> performanceCurveOfDate;

    /**
     * 绩效曲线报表数据加载方法
     */
    public String loadData() {
        Date startTime = TimeUtils.toDate(begin);
        Date stopTime = TimeUtils.toDate(end);
        performanceCurve = performanceCurveService.getPerformanceCurve(startTime, stopTime);
        performanceCurveOfDate = performanceCurveService.getPerformanceCurveOfDays(startTime, stopTime);

        return JSON;
    }

    /** -----------------------------getter and setter------------------------------------- **/

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setPerformanceCurveService(PerformanceCurveService performanceCurveService) {
        this.performanceCurveService = performanceCurveService;
    }

    public PerformanceIndicators getPerformanceCurve() {
        return performanceCurve;
    }

    public List<PerformanceIndicators> getPerformanceCurveOfDate() {
        return performanceCurveOfDate;
    }
}
