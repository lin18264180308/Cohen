package com.mrong.lineview.admin.dao;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.dao.IBaseDao;

public interface PerformanceCurveDao extends IBaseDao {
    /**
     * 查询线效率的数据
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Object[]> getLineEffectiveRunningData(Date startTime, Date stopTime);

    /**
     * 查询线占用的数据
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Object[]> getLineOccupancyData(Date startTime, Date stopTime);

    /**
     * 获取计划生产数据(st)，总时间减非计划时间
     * 
     * @param startTime
     *            : 开始时间
     * @param stopTime
     *            : 结束时间
     */
    public List<Object[]> getPlanData(Date startTime, Date stopTime);

    /**
     * 计算一段时间内的瓶产量
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Object[]> getYieldData(Date startTime, Date stopTime);
}
