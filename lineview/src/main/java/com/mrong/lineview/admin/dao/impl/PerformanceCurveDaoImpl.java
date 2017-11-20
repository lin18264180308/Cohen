package com.mrong.lineview.admin.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.PerformanceCurveDao;
import com.mrong.lineview.common.dao.impl.BaseDaoImpl;

/**
 * 指标数据查询Dao
 * 
 * @author 林金成
 *         2017年11月20日
 */
@SuppressWarnings("all")
public class PerformanceCurveDaoImpl extends BaseDaoImpl implements PerformanceCurveDao {

    /**
     * 查询线有效运转的数据
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Object[]> getLineEffectiveRunningData(Date startTime, Date stopTime) {
        /** 查询参数 **/
        Map<String, Object> propertyMap = new HashMap<>();
        /** 查询模式时间 **/
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :endTime) OR (STOP_TIME IS NULL AND START_TIME < :endTime))";
        propertyMap.put("begin", startTime);
        propertyMap.put("end", stopTime);
        propertyMap.put("patternClasses", "外部原因");
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);

        return data;
    }

    /**
     * 查询线占用的数据
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Object[]> getLineOccupancyData(Date startTime, Date stopTime) {
        /** 查询参数 **/
        Map<String, Object> propertyMap = new HashMap<>();
        /** 查询模式时间 **/
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :endTime) OR (STOP_TIME IS NULL AND START_TIME < :endTime))";
        propertyMap.put("startTime", startTime);
        propertyMap.put("endTime", stopTime);
        propertyMap.put("patternClasses", "计划停机");
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);

        return data;
    }

    /**
     * 获取计划生产数据(st)，总时间减非计划时间
     * 
     * @param startTime
     *            : 开始时间
     * @param stopTime
     *            : 结束时间
     */
    public List<Object[]> getPlanData(Date startTime, Date stopTime) {
        /** 查询参数 **/
        Map<String, Object> propertyMap = new HashMap<>();
        /** 查询模式时间 **/
        String sql = "SELECT START_TIME, STOP_TIME FROM mode_record WHERE PATTERN_CLASSES = :patternClasses AND ((STOP_TIME IS NOT NULL AND STOP_TIME BETWEEN :startTime AND :endTime) OR (STOP_TIME IS NULL AND START_TIME < :endTime))";
        propertyMap.put("patternClasses", "非计划时间");
        propertyMap.put("startTime", startTime);
        propertyMap.put("endTime", stopTime);
        List<Object[]> data = (List<Object[]>) this.getBySql(sql, propertyMap);

        return data;
    }

    /**
     * 计算一段时间内的瓶产量
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Object[]> getYieldData(Date startTime, Date stopTime) {
        /** 查询参数 **/
        Map<String, Object> propertyMap = new HashMap<>();
        /** 查询瓶产量 **/
        String sql = "SELECT q1, sptime FROM speed WHERE sptime BETWEEN :startTime AND :stopTime";

        propertyMap.put("startTime", startTime);
        propertyMap.put("stopTime", stopTime);
        List<Object[]> data = (List<Object[]>) getBySql(sql, propertyMap);

        return data;
    }
}
