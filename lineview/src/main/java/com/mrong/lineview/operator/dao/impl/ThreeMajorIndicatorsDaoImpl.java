package com.mrong.lineview.operator.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.SpeedMachine;
import com.mrong.lineview.operator.dao.ThreeMajorIndicatorsDao;
import com.mrong.lineview.util.TimeUtils;

/**
 * 机台一览的三大指标
 * 
 * @author 张裕宝
 */
@SuppressWarnings("all")
public class ThreeMajorIndicatorsDaoImpl extends BaseDaoImpl implements ThreeMajorIndicatorsDao {
    /* 单位秒 */
    // 计划生产时间
    Double st = 0.0;
    // 有效生产时间
    Double ept = 0.0;
    // 线占用生产时间
    Double lt = 0.0;
    // 线有效运转时间
    Double let = 0.0;

    /**
     * 三大指标计算
     * 
     * @param startTime
     *            :开始时间
     * @param stopTime:结束时间
     * @return 单位 ：分钟
     */
    public Map<String, Double> indexCalculation(Date startTime, Date stopTime) {

        Map<String, Double> map = new HashMap<>();
        Map<String, Object> propertyMap = new HashMap<>();
        /* 旧的获取酒机瓶数 List<?> lists = null; 有效生产时间EPT 公式=（查询时间内）酒机实际产量/4000瓶/h
         * 加一个判断，若当前时间段跨两个模式则，时间段自动改为当前模式开始的时间 到时间段的结束时间 // 获取当前模式的开始时间 String sql =
         * "SELECT start_time FROM mode_record WHERE id=(SELECT max(id) FROM mode_record)"
         * ; Map<String, Object> propertyMap = new HashMap<>(); List<?> times =
         * this.getBySql(sql, null); if (times.size() == 0 || times == null) { ept =
         * 0.0; } else { Date modeStartTime = (Date) times.get(0); Double temp =
         * (double) TimeUtils.calculate(modeStartTime, startTime); sql =
         * "SELECT q1 FROM speed WHERE sptime BETWEEN :begin AND :end";
         * propertyMap.clear(); propertyMap.put("end", stopTime);
         * propertyMap.put("begin", (temp >= 0) ? modeStartTime : startTime); lists =
         * this.getBySql(sql, propertyMap); if (lists.size() == 0 || lists == null) { //
         * TODO } else { // 获得当前时间段内最小的灌装机灌装数 Integer min = (Integer) lists.get(0); //
         * 获得当前时间段内最大的灌装数 Integer max = (Integer) lists.get(lists.size() - 1); // 单位 秒
         * ept = ((double) max - (double) min) * 3600 / 40000; } } */
        /**
         * 根据查询时间获得灌酒机的瓶数
         */
        String hql = "FROM SpeedMachine s WHERE s.sptime BETWEEN :startTime AND :stopTime";
        Map<String, Object> mapzz = new HashMap<>();
        mapzz.put("startTime", startTime);
        mapzz.put("stopTime", stopTime);
        List<?> lists = getByHql(hql, mapzz);
        List<SpeedMachine> speedMachines = (List<SpeedMachine>) lists;
        // 洗瓶机实际生产速度
        double bottleWasher = 0.0;
        // 灌酒机实际生产速度
        double fillingMachine = 0.0;
        // 杀菌机实际生产速度
        double sterilizerMachine = 0.0;
        // 贴标机实际生产速度
        double labelingMachine = 0.0;
        // 损失时间（酒机查询时间内的产量）
        int fillingMachineCount = 0;
        // 临时记录清零的记录
        List<Integer> tempCount = new ArrayList<>();
        // 临时灌酒机数量
        int tempfillingMachine = 0;
        for (SpeedMachine s : speedMachines) {
            bottleWasher += s.getS1();
            fillingMachine += s.getS2() * 1.64;
            sterilizerMachine += s.getS3() * 1000;
            labelingMachine += s.getS4();
            // 根据灌酒机清零时间，分别执行以下操作
            int i = 0;
            i++;
            if (tempfillingMachine > s.getQ1()) {
                tempCount.add(i);
            }
            tempfillingMachine = s.getQ1();

        }
        Map<String, Double> speedmap = new HashMap<>();
        speedmap.put("bottleWasher", bottleWasher);
        speedmap.put("fillingMachine", fillingMachine);
        speedmap.put("sterilizerMachine", sterilizerMachine);
        speedmap.put("labelingMachine", labelingMachine);
        if (speedMachines.size() > 0) {
            // 当没有清零操作的时候，酒机查询时间内产生的数量
            if (tempCount.size() == 0 || tempCount == null) {
                fillingMachineCount = speedMachines.get(speedMachines.size() - 1).getQ1()
                        - speedMachines.get(0).getQ1();
            } else {
                // 有清零操作的时候
                // 分为
                // 有多次清零操作的话
                if (tempCount.size() >= 1) {
                    int temp0 = 0;
                    for (int i = 1; i < tempCount.size(); i++) {
                        temp0 += speedMachines.get(tempCount.get(i)).getQ1()
                                - speedMachines.get(tempCount.get(i - 1)).getQ1();
                    }
                    fillingMachineCount = speedMachines.get(tempCount.get(0) - 1).getQ1() - speedMachines.get(0).getQ1()
                            + temp0;
                } else {
                    // 有一次清零操作(为0记录的上一条-查询的开始时间灌酒机的瓶数+查询时间的结束时间的瓶数-为零记录的那个瓶数)
                    fillingMachineCount = speedMachines.get(tempCount.get(0) - 1).getQ1() - speedMachines.get(0).getQ1()
                            + speedMachines.get(speedMachines.size() - 1).getQ1()
                            - speedMachines.get(tempCount.get(0)).getQ1();
                }
            }
        }
        ept = ((double) fillingMachineCount * 3600 / 40000);

        /* 线占用生产时间=当前查询时间段-模式无需求-模式计划停机的时间 */
        // 获取查询时间，单位s
        Double queryTime = (double) TimeUtils.calculate(stopTime, startTime);
        // 获取模式无需求的时间
        Double notPlan = 0.0;
        String sql1 = "SELECT start_time FROM mode_record WHERE (start_time BETWEEN :begin AND :end) AND pattern_classes = :patternClasses";
        String sql2 = "SELECT stop_time FROM mode_record WHERE (SELECT max(id) FROM mode_record WHERE pattern_classes = :patternClasses)";

        propertyMap.clear();
        propertyMap.put("begin", startTime);
        propertyMap.put("end", stopTime);
        propertyMap.put("patternClasses", "非计划时间");
        List<?> notPlanTime = this.getBySql(sql1, propertyMap);

        // 获取无需求最后一条记录进行判断
        propertyMap.clear();
        propertyMap.put("patternClasses", "非计划时间");
        List<?> lastTime = this.getBySql(sql2, propertyMap);

        if (notPlanTime.size() == 0 || notPlanTime == null) {
            // TODO
        } else {
            Date min1 = (Date) notPlanTime.get(0);
            if (lastTime.size() == 0 || lastTime == null) {
                notPlan = (double) TimeUtils.calculate(new Date(), min1);
            } else {
                Date max1 = (Date) lastTime.get(0);
                notPlan = (double) TimeUtils.calculate(max1, min1);
            }
        }
        // 获得模式计划停机时间
        Double planStop = 0.0;

        propertyMap.clear();
        propertyMap.put("begin", startTime);
        propertyMap.put("end", stopTime);
        propertyMap.put("patternClasses", "计划停机");
        List<?> planStopTime = this.getBySql(sql1, propertyMap);

        // 获取无需求最后一条记录进行判断
        propertyMap.clear();
        propertyMap.put("patternClasses", "计划停机");
        List<?> lastTimep = this.getBySql(sql2, propertyMap);

        if (planStopTime.size() == 0 || planStopTime == null) {
            // TODO
        } else {
            Date min2 = (Date) planStopTime.get(0);
            if (lastTimep.size() == 0 || lastTimep == null) {
                planStop = (double) TimeUtils.calculate(new Date(), min2);
            } else {
                Date max2 = (Date) lastTimep.get(0);
                planStop = (double) TimeUtils.calculate(max2, min2);
            }
        }
        // 获得外部因素停机的时间
        Double externalFactors = 0.0;

        propertyMap.clear();
        propertyMap.put("begin", startTime);
        propertyMap.put("end", stopTime);
        propertyMap.put("patternClasses", "外部原因");
        List<?> externalFactorsTime = this.getBySql(sql1, propertyMap);

        // 获取无需求最后一条记录进行判断
        propertyMap.clear();
        propertyMap.put("patternClasses", "外部原因");
        List<?> lastTimeef = this.getBySql(sql2, propertyMap);

        if (externalFactorsTime.size() == 0 || externalFactorsTime == null) {
            // TODO
        } else {
            Date min3 = (Date) externalFactorsTime.get(0);
            if (lastTimeef.size() == 0 || lastTimeef == null) {
                externalFactors = (double) TimeUtils.calculate(new Date(), min3);
            } else {
                Date max3 = (Date) lastTimeef.get(0);
                externalFactors = (double) TimeUtils.calculate(max3, min3);
            }
        }

        // 单位秒
        lt = queryTime - notPlan - planStop;
        // 单位为秒
        st = queryTime - notPlan;
        // 单位为秒
        let = lt - externalFactors;
        map.put("ept", ept);
        map.put("lt", lt);
        map.put("st", st);
        map.put("let", let);

        return map;
    }
}
