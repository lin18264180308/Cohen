package com.mrong.lineview.admin.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.dao.IBaseDao;
import com.mrong.lineview.common.entity.Entry;

public interface LossOfSpeedDao extends IBaseDao {

    /**
     * 统计计划停机状态
     */
    public Map<String, Object> getScheduledDownTime(Date startTime, Date stopTime);

    /**
     * 获取ept(有效生产时间)指标
     */
    public double getEpt(Date startTime, Date stopTime);

    /**
     * 获取计划生产时间(st)，总时间减非计划时间
     * 
     * @param startTime
     *            : 开始时间
     * @param stopTime
     *            : 结束时间
     */
    public double getPlanTime(Date startTime, Date stopTime);

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
    public double getLineOccupancy(Date startTime, Date stopTime, double st);

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
    public double getLineEffectiveRunningTime(Date startTime, Date stopTime, double lt);

    /**
     * 获取所有机台时间范围内所有的白条状态
     */
    public double getMachineStopInWhite(Date startTime, Date stopTime, String machineName);

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
    public Map<String, Double> getSpeed(Date startTime, Date stopTime);

    /**
     * 获取生产时间
     * 
     * @param startTime
     * @param stopTime
     */
    public double getProductionTime(Date startTime, Date stopTime);

    /**
     * 获取外部损失时间（外部原因模式总时间）
     * 
     * @param startTime
     * @param stopTime
     */
    public double getExternalLossTime(Date startTime, Date stopTime);

    /**
     * 获取灌装机大小停机时间
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Entry<String, Double>> getBigStopOfFillingMachine(Date startTime, Date stopTime);

    /**
     * 获取无生产需求时间
     * 
     * @param startTime
     * @param stopTime
     */
    public double getNoProductionDemand(Date startTime, Date stopTime);

    /**
     * 计算一段时间内的瓶产量
     * 
     * @param startTime
     * @param stopTime
     */
    public int getYield(Date startTime, Date stopTime);
}
