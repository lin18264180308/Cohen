package com.mrong.lineview.admin.dao.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.common.entity.SpeedMachine;
import com.mrong.lineview.util.Colors;
import com.mrong.lineview.util.Machines;
import com.mrong.lineview.util.MathUtils;
import com.mrong.lineview.util.TimeUtils;

@SuppressWarnings("all")
public class LossOfSpeedDaoImpl extends BaseDaoImpl implements LossOfSpeedDao {

    /**
     * 统计计划停机状态
     */
    public Map<String, Object> getScheduledDownTime(Date startTime, Date stopTime) {
        Map<String, Long> result = null;
        long duration = 0;

        Map<String, Object> propertyMap = new HashMap<>();
        String sql = "SELECT MODE_NAME, START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :stopTime) OR (STOP_TIME IS NULL AND START_TIME < :stopTime))";

        propertyMap.put("patternClasses", "计划停机");
        propertyMap.put("startTime", startTime);
        propertyMap.put("stopTime", stopTime);

        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);
        if (data != null && data.size() > 0) {
            String modeName = null;
            Date begin = null;
            Date end = null;
            result = new HashMap<>();
            for (Object[] objs : data) {
                modeName = (String) objs[0];
                begin = (Date) objs[1];
                end = (Date) objs[2];
                if (begin.before(startTime)) {
                    begin = startTime;
                }
                if (end == null) {
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                duration += TimeUtils.getDisparityOfSecond(begin, end);
                if (!result.keySet().contains(modeName)) {// map 中没有该模式的记录，直接添加进去
                    result.put(modeName, TimeUtils.getDisparityOfSecond(begin, end));
                } else {// 该模式已经存在记录，将时间叠加进去
                    result.put(modeName, result.get(modeName) + TimeUtils.getDisparityOfSecond(begin, end));
                }
            }
        }
        propertyMap.clear();
        propertyMap.put("scheduledDown", result);
        propertyMap.put("duration", duration);

        return propertyMap;
    }

    /**
     * 获取ept(有效生产时间)指标
     */
    public double getEpt(Date startTime, Date stopTime) {

        int fillingMachineCount = this.getYield(startTime, stopTime);

        double result = ((double) fillingMachineCount * 3600 / 40000);

        return result;
    }

    /**
     * 获取计划生产时间(st)，总时间减非计划时间
     * 
     * @param startTime
     *            : 开始时间
     * @param stopTime
     *            : 结束时间
     */
    public double getPlanTime(Date startTime, Date stopTime) {
        double result = 0;
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :endTime) OR (STOP_TIME IS NULL AND START_TIME < :endTime))";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("patternClasses", "非计划时间");
        propertyMap.put("startTime", startTime);
        propertyMap.put("endTime", stopTime);
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);
        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {
                    begin = startTime;
                }
                if (end == null) {
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                result += TimeUtils.getDisparityOfSecond(begin, end);
            }
        }
        result = TimeUtils.getDisparityOfSecond(startTime, stopTime) - result;// 总时间减去非计划时间，得到计划生产时间

        return result;
    }

    /**
     * 获取线占用生产时间(lt)
     * 
     * @param startTime
     *            : 开始时间
     * @param stopTime
     *            : 结束时间
     * @param st
     *            : 计划生产时间
     */
    public double getLineOccupancy(Date startTime, Date stopTime, double st) {
        Map<String, Object> propertyMap = new HashMap<>();
        // 获得模式计划停机时间
        long planStop = 0;
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :endTime) OR (STOP_TIME IS NULL AND START_TIME < :endTime))";

        propertyMap.put("startTime", startTime);
        propertyMap.put("endTime", stopTime);
        propertyMap.put("patternClasses", "计划停机");
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);

        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {
                    begin = startTime;
                }
                if (end == null) {
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                planStop += TimeUtils.getDisparityOfSecond(begin, end);
            }
        }

        return st - planStop;
    }

    /**
     * 获取线有效运转时间(let)
     * 
     * @param startTime
     *            : 开始时间
     * @param stopTime
     *            : 结束时间
     * @param lt
     *            : 线占用时间
     */
    public double getLineEffectiveRunningTime(Date startTime, Date stopTime, double lt) {
        // 获得外部因素停机的时间
        long externalFactors = 0;
        Map<String, Object> propertyMap = new HashMap<>();
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :begin AND :end) OR (STOP_TIME IS NULL AND START_TIME < :end))";
        propertyMap.put("begin", startTime);
        propertyMap.put("end", stopTime);
        propertyMap.put("patternClasses", "外部原因");
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);

        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {
                    begin = startTime;
                }
                if (end == null) {
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                externalFactors += TimeUtils.getDisparityOfSecond(begin, end);
            }
        }

        return lt - externalFactors;
    }

    /**
     * 获取所有机台时间范围内所有的白条状态
     */
    public double getMachineStopInWhite(Date startTime, Date stopTime, String machineName) {
        long result = 0;
        String sql = "SELECT begin_time, end_time FROM mechine_stop_information WHERE"
                + " machine_name = :machineName AND color = :color AND ((end_time IS NOT NULL AND end_time BETWEEN :startTime AND :stopTime) or (end_time IS NULL AND begin_time < :stopTime))";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("machineName", machineName);
        propertyMap.put("color", Colors.WHITE.getName());
        propertyMap.put("startTime", startTime);
        propertyMap.put("stopTime", stopTime);
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);
        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {
                    begin = startTime;
                }
                if (end == null) {
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                result += TimeUtils.getDisparityOfSecond(begin, end);
            }
        }

        return result;
    }

    /**
     * 管理员界面的速度损失
     * 速度区域
     * 
     * @param startTime
     *            查询开始时间
     * @param stopTime
     *            查询结束时间
     * @return
     */
    public Map<String, Double> getSpeed(Date startTime, Date stopTime) {

        //从数据库中查询实际生产速度
        String hql = "FROM SpeedMachine s WHERE s.sptime > :startTime AND s.sptime < :stopTime";
        Map<String, Object> map = new HashMap<>();
        map.put("startTime", startTime);
        map.put("stopTime", stopTime);
        List<SpeedMachine> speedMachines = (List<SpeedMachine>) getByHql(hql, map);
        //洗瓶机实际生产速度
        double bottleWasher = 0.0;
        //灌酒机实际生产速度
        double fillingMachine = 0.0;
        //杀菌机实际生产速度
        double sterilizerMachine = 0.0;
        //贴标机实际生产速度
        double labelingMachine = 0.0;
        //损失时间（酒机查询时间内的产量）
        int fillingMachineCount = 0;
        //临时记录清零的记录
        List<Integer> tempCount = new ArrayList<>();
        //临时灌酒机数量
        int tempfillingMachine = 0;
        for (SpeedMachine s : speedMachines) {
            bottleWasher += s.getS1();
            fillingMachine += s.getS2() * 1.64;
            sterilizerMachine += s.getS3() * 1000;
            labelingMachine += s.getS4();
            //根据灌酒机清零时间，分别执行以下操作
            int i = 0;
            i++;
            if (tempfillingMachine > s.getQ1()) {
                tempCount.add(i);
            }
            tempfillingMachine = s.getQ1();

        }
        Map<String, Double> speedmap = new HashMap<>();

        speedmap.put("bottleWasher", MathUtils.formatTwoBit(bottleWasher));
        speedmap.put("fillingMachine", MathUtils.formatTwoBit(fillingMachine));
        speedmap.put("sterilizerMachine", MathUtils.formatTwoBit(sterilizerMachine));
        speedmap.put("labelingMachine", MathUtils.formatTwoBit(labelingMachine));
        if (speedMachines.size() > 0) {
            //当没有清零操作的时候，酒机查询时间内产生的数量
            if (tempCount.size() == 0 || tempCount == null) {
                fillingMachineCount = speedMachines.get(speedMachines.size() - 1).getQ1()
                        - speedMachines.get(0).getQ1();
            } else {
                //有清零操作的时候
                //分为
                //有多次清零操作的话
                if (tempCount.size() >= 1) {
                    int temp0 = 0;
                    for (int i = 1; i < tempCount.size(); i++) {
                        temp0 += speedMachines.get(tempCount.get(i)).getQ1()
                                - speedMachines.get(tempCount.get(i - 1)).getQ1();
                    }
                    fillingMachineCount = speedMachines.get(tempCount.get(0) - 1).getQ1() - speedMachines.get(0).getQ1()
                            + temp0;
                } else {
                    //有一次清零操作(为0记录的上一条-查询的开始时间灌酒机的瓶数+查询时间的结束时间的瓶数-为零记录的那个瓶数)
                    fillingMachineCount = speedMachines.get(tempCount.get(0) - 1).getQ1() - speedMachines.get(0).getQ1()
                            + speedMachines.get(speedMachines.size() - 1).getQ1()
                            - speedMachines.get(tempCount.get(0)).getQ1();
                }
            }
        }
        speedmap.put("fillingMachineCount", (double) fillingMachineCount);
        //查询无生产需求的时间
        long noProductionDemandTime = 0;
        String sql1 = "SELECT START_TIME, DURATION FROM mode_record WHERE START_TIME>:startTime AND STOP_TIME<:stopTime AND PATTERN_CLASSES='非计划时间'";
        List<?> moderecords = getBySql(sql1, map);
        if (moderecords == null || moderecords.size() == 0) {

        } else {
            List<Object[]> tempModeRecords = (List<Object[]>) moderecords;
            BigInteger duration = null;
            Date beginTime = null;
            for (Object[] objs : tempModeRecords) {
                beginTime = (Date) objs[0];
                duration = (BigInteger) objs[1];
                noProductionDemandTime += duration.longValue();
                //如果有持续时间等于零的情况
                if (duration.longValue() == 0) {
                    noProductionDemandTime += TimeUtils.calculate(stopTime, beginTime);
                }

            }
        }
        speedmap.put("noProductionDemandTime", (double) noProductionDemandTime);
        return speedmap;
    }

    /**
     * 获取生产时间
     * 计划生产时间减去酒机停机的时间
     * 
     * @param startTime
     * @param stopTime
     */
    public double getProductionTime(Date startTime, Date stopTime) {
        long result = 0;

        /** 查询计划生产时间 **/
        Map<String, Object> propertyMap = new HashMap<>();
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :stopTime) OR (STOP_TIME IS NULL AND START_TIME < :stopTime))";
        propertyMap.put("patternClasses", "计划生产");
        propertyMap.put("startTime", startTime);
        propertyMap.put("stopTime", stopTime);
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);
        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {
                    begin = startTime;
                }
                if (end == null) {
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                result += TimeUtils.getDisparityOfSecond(begin, end);// 累加计划生产模式的持续时间
            }
        }

        /** 计划生产时间扣除酒机停机时间 **/
        sql = "SELECT begin_time, end_time FROM mechine_stop_information WHERE machine_name = :machineName AND color = :color AND ((end_time IS NOT NULL AND end_time BETWEEN :begin AND :end) OR (end_time IS NULL AND begin_time < :end))";
        propertyMap.clear();
        propertyMap.put("machineName", Machines.FILLINGMACHINE.getName());
        propertyMap.put("begin", startTime);
        propertyMap.put("end", stopTime);
        propertyMap.put("color", Colors.GREEN.getName());
        data = (List<Object[]>) this.getBySql(sql, propertyMap);
        long duration = 0;
        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {// 如果当前模式的开始时间超出了开始时间确定的范围，则将begin设置为范围开始时间
                    begin = startTime;
                }
                if (end == null) {// 如果当前模式的结束时间为空，说明当前模式还在持续，则将结束时间设置为当前时间
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                duration += TimeUtils.getDisparityOfSecond(begin, end);// 累加计划生产模式的持续时间
            }
        }
        // 计划生产时间减去酒机停机时间
        result = result - (TimeUtils.getDisparityOfSecond(startTime, stopTime) - duration);

        return result < 0 ? 0 : result;
    }

    /**
     * 获取外部损失时间（外部原因模式总时间）
     * 
     * @param startTime
     * @param stopTime
     */
    public double getExternalLossTime(Date startTime, Date stopTime) {
        long result = 0;

        /** 查询计划生产时间 **/
        Map<String, Object> propertyMap = new HashMap<>();
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :stopTime) OR (STOP_TIME IS NULL AND START_TIME < :stopTime))";
        propertyMap.put("patternClasses", "外部原因");
        propertyMap.put("startTime", startTime);
        propertyMap.put("stopTime", stopTime);
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);
        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {// 如果当前模式的开始时间超出了开始时间确定的范围，则将begin设置为范围开始时间
                    begin = startTime;
                }
                if (end == null) {// 如果当前模式的结束时间为空，说明当前模式还在持续，则将结束时间设置为当前时间
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                result += TimeUtils.getDisparityOfSecond(begin, end);// 累加计划生产模式的持续时间
            }
        }

        return result;
    }

    /**
     * 获取灌装机大、小停机时间
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Entry<String, Double>> getBigStopOfFillingMachine(Date startTime, Date stopTime) {
        List<Entry<String, Double>> result = new ArrayList<>();

        long bigStop = 0;
        long smallStop = 0;
        long duration = 0;
        /** 查询灌装机大停机时间 **/
        Map<String, Object> propertyMap = new HashMap<>();
        String sql = "SELECT begin_time, end_time FROM mechine_stop_information WHERE machine_name = :machineName AND color != :color AND ((end_time IS NOT NULL AND end_time BETWEEN :begin AND :end) OR (end_time IS NULL AND begin_time < :end))";
        propertyMap.put("machineName", Machines.FILLINGMACHINE.getName());
        propertyMap.put("color", Colors.GREEN.getName());
        propertyMap.put("begin", startTime);
        propertyMap.put("end", stopTime);
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);

        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {// 如果当前模式的开始时间超出了开始时间确定的范围，则将begin设置为范围开始时间
                    begin = startTime;
                }
                if (end == null) {// 如果当前模式的结束时间为空，说明当前模式还在持续，则将结束时间设置为当前时间
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                duration = TimeUtils.getDisparityOfSecond(begin, end);
                if (duration >= 5) {
                    bigStop += duration;// 累加大停机持续时间
                } else {
                    smallStop += duration;// 累加小停机持续时间
                }
            }
        }
        duration = TimeUtils.getDisparityOfSecond(startTime, stopTime);
        result.add(new Entry<String, Double>("大停机", MathUtils.formatTwoBit((double) (bigStop / duration))));
        result.add(new Entry<String, Double>("小停机", MathUtils.formatTwoBit((double) (smallStop / duration))));

        return result;
    }

    /**
     * 获取无生产需求时间
     * 
     * @param startTime
     * @param stopTime
     */
    public double getNoProductionDemand(Date startTime, Date stopTime) {
        long result = 0;
        Map<String, Object> propertyMap = new HashMap<>();
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :stopTime) OR (STOP_TIME IS NULL AND START_TIME < :stopTime))";
        propertyMap.put("patternClasses", "非计划时间");
        propertyMap.put("startTime", startTime);
        propertyMap.put("stopTime", stopTime);
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);
        if (data != null && data.size() > 0) {
            Date begin = null;
            Date end = null;
            for (Object[] objs : data) {
                begin = (Date) objs[0];
                end = (Date) objs[1];
                if (begin.before(startTime)) {// 如果当前模式的开始时间超出了开始时间确定的范围，则将begin设置为范围开始时间
                    begin = startTime;
                }
                if (end == null) {// 如果当前模式的结束时间为空，说明当前模式还在持续，则将结束时间设置为当前时间
                    end = new Date();
                }
                if (end.after(stopTime)) {
                    end = stopTime;
                }
                result += TimeUtils.getDisparityOfSecond(begin, end);// 累加非计划时间模式的持续时间
            }
        }

        return result;
    }

    /**
     * 计算一段时间内的瓶产量
     * 
     * @param startTime
     * @param stopTime
     */
    public int getYield(Date startTime, Date stopTime) {
        int result = 0;

        String sql = "SELECT q1 FROM speed WHERE sptime BETWEEN :startTime AND :stopTime";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("startTime", startTime);
        propertyMap.put("stopTime", stopTime);
        List<?> data = getBySql(sql, propertyMap);

        if (data != null && data.size() > 0) {
            int first = (Integer) data.get(0);// 集合第一条数据
            int previous = 0;// 上一次的瓶数
            boolean flg = true;
            for (Object obj : data) {
                int count = (int) obj;// 当前记录的瓶数
                if (count < previous) {// 如果当前记录的瓶数小于上一条记录的瓶数，则两条记录中间有清零操作
                    if (flg) {// flg为true，没有执行过（previous - first）
                        result = result + (previous - first);// 则上一条记录为上一次清零后至这一次清零操作中间最后一条记录，用上一条记录的瓶数减去清零后第一条记录的瓶数，得到本次清零前一段的总产量，叠加到result
                        flg = false;
                    } else {// flg为false，执行过（previous - first）,不在执行
                        result = result + previous;
                    }
                    first = count;
                }
                previous = count;// 将本次的数量设置为 上一条数量
            }
            if (flg) {// flg为true，没有执行过（previous - first）
                result = result + (previous - first);// 则上一条记录为上一次清零后至这一次清零操作中间最后一条记录，用上一条记录的瓶数减去清零后第一条记录的瓶数，得到本次清零前一段的总产量，叠加到result
                flg = false;
            } else {// flg为false，执行过（previous - first）,不在执行
                result = result + previous;
            }
        }

        return result;
    }
}
