package com.mrong.lineview.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.admin.dao.PerformanceCurveDao;
import com.mrong.lineview.admin.entity.PerformanceIndicators;
import com.mrong.lineview.admin.service.PerformanceCurveService;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.BigTimeUtils;

/**
 * 绩效曲线报表service
 * 
 * @author 林金成
 *         2017年11月17日
 */
@SuppressWarnings("all")
public class PerformanceCurveServiceImpl implements PerformanceCurveService {

    private LossOfSpeedDao lossOfSpeedDao;
    private PerformanceCurveDao performanceCurveDao;

    public void setLossOfSpeedDao(LossOfSpeedDao lossOfSpeedDao) {
        this.lossOfSpeedDao = lossOfSpeedDao;
    }

    public void setPerformanceCurveDao(PerformanceCurveDao performanceCurveDao) {
        this.performanceCurveDao = performanceCurveDao;
    }

    /**
     * 获得总的OEE/LEF/GLY/产量
     */
    @Override
    public PerformanceIndicators getPerformanceCurve(Date startTime, Date stopTime) {
        PerformanceIndicators result = null;
        Map<String, Double> map = this.getPerformanceIndex(startTime, stopTime);

        result = new PerformanceIndicators(map.get("oee"), map.get("lef"), map.get("gly"), map.get("yield"));
        return result;
    }

    /**
     * 获得每天的OEE/LEF/GLY/产量
     */
    @Override
    public List<PerformanceIndicators> getPerformanceCurveOfDays(Date startTime, Date stopTime) {
        List<PerformanceIndicators> result = new ArrayList<>();

        int day1 = startTime.getDate();
        int day2 = stopTime.getDate();
        Date begin = null;
        Date end = null;
        int count = day2 - day1;

        /** ---- 循环遍历，计算每天的绩效和产量 ------ **/
        for (int i = 1; i <= count; i++) {
            if (i == 1 && count == 1) {
                begin = startTime;
                end = stopTime;
            } else if (i == 1) {
                begin = startTime;
                end = BigTimeUtils.getCurrentDayEndTime();
                end.setDate(begin.getDate());
            } else if (i == count) {
                end = stopTime;
                begin = BigTimeUtils.getCurrentDayStartTime();
                begin.setDate(end.getDate());
            } else {
                begin = BigTimeUtils.getCurrentDayStartTime();
                end = BigTimeUtils.getCurrentDayEndTime();
                begin.setDate(day1 + i - 1);
                end.setDate(day1 + i - 1);
            }

            List<Entry<String, Double>> list = new ArrayList<>();
            Map<String, Double> map = this.getPerformanceIndex(begin, end);

            result.add(new PerformanceIndicators(map.get("oee"), map.get("lef"), map.get("gly"), map.get("yield")));
        }

        return result;
    }

    /**
     * 获取绩效三大指标和产量
     * 
     * @param startTime
     * @param stopTime
     */
    private Map<String, Double> getPerformanceIndex(Date startTime, Date stopTime) {
        Map<String, Double> result = new HashMap<>();
        double yield = (double) lossOfSpeedDao.getYield(startTime, stopTime);
        double ept = yield * 3600 / 40000;
        double st = lossOfSpeedDao.getPlanTime(startTime, stopTime);
        double lt = lossOfSpeedDao.getLineOccupancy(startTime, stopTime, st);
        double let = lossOfSpeedDao.getLineEffectiveRunningTime(startTime, stopTime, lt);

        double oee = ept * 100 / lt;
        double gly = ept * 100 / st;
        double lef = ept * 100 / let;

        result.put("oee", oee);
        result.put("gly", gly);
        result.put("lef", lef);
        result.put("yield", (double) lossOfSpeedDao.getYield(startTime, stopTime));

        return result;
    }

    /**
     * 计算保存当前天的绩效指标
     */
    public String savePerformanceIndicatorsOfCurrentDay(Date startTime, Date stopTime) {
        String result = null;

        Map<String, Double> data = this.getPerformanceIndex(startTime, stopTime);
        String sql = "INSERT INTO PerformanceIndicators (id, oee, gly, lef, yield, curr_time) VALUES (NULL, :oee, :gly, :lef, :yield, :curr_time)";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("oee", data.get("oee"));
        propertyMap.put("gly", data.get("gly"));
        propertyMap.put("lef", data.get("lef"));
        propertyMap.put("yield", data.get("yield"));
        propertyMap.put("curr_time", startTime);

        int m = this.lossOfSpeedDao.executeBySql(sql, propertyMap);
        if (m <= 0) {
            result = new Date().toString() + " : " + startTime.toString() + " 至  " + stopTime.toString()
                    + "绩效指标计算保存失败！";
        } else {
            result = null;
        }

        return result;
    }
}
