package com.mrong.lineview.operator.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.operator.dao.ITimeChooseDao;
import com.mrong.lineview.operator.entity.MechineStopInformationBean;
import com.mrong.lineview.operator.entity.TimeChooseParameterBean;
import com.mrong.lineview.operator.entity.TimeChooseStatusBean;
import com.mrong.lineview.operator.service.ITimeChooseService;
import com.mrong.lineview.util.Machines;
import com.mrong.lineview.util.MathUtils;
import com.mrong.lineview.util.TimeUtils;

/**
 * 时间选择功能，获得用户选择的起始和结束时间，获取该时间段内所有机台的停机或者非停机状态，计算出相应的柱状图宽度返回至前台
 * 
 * @author 林金成
 *         2017年10月18日
 */
@SuppressWarnings("all")
public class TimeChooseServiceImpl implements ITimeChooseService {

    private ITimeChooseDao timeChooseDao;

    public void setTimeChooseDao(ITimeChooseDao timeChooseDao) {
        this.timeChooseDao = timeChooseDao;
    }

    public Map<String, List<TimeChooseStatusBean>> getStatusWithTimeChoose(TimeChooseParameterBean params) {
        List<MechineStopInformationBean> data = getInfomationIfMechineStop(params.getBegin(), params.getEnd());
        Map<String, List<TimeChooseStatusBean>> result = processingDataOfMechineStop(data, params.getBegin(),
                params.getEnd(), params.getMaxWidth());

        return result;
    }

    /**
     * 检索时间范围内的停机数据
     * 
     * @param begin
     *            : 时间选择的起始时间
     * @param end
     *            : 时间选择的结束时间
     */
    @SuppressWarnings("unchecked")
    private List<MechineStopInformationBean> getInfomationIfMechineStop(Date begin, Date end) {
        List<MechineStopInformationBean> result = null;
        String sql = "SELECT machine_name, begin_time, end_time, color FROM mechine_stop_information WHERE (end_time IS NOT NULL AND end_time BETWEEN :begin AND :end) OR (end_time IS NULL and begin_time < :end)";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("begin", begin);
        propertyMap.put("end", end);
        List<Object[]> data = (List<Object[]>) timeChooseDao.getBySql(sql, propertyMap);
        result = new ArrayList<>();
        MechineStopInformationBean tmp = null;
        if (data != null && data.size() > 0) {
            String machineName = null;
            Date startTime = null;
            Date stopTime = null;
            String color = null;
            for (Object[] objs : data) {
                machineName = (String) objs[0];
                startTime = (Date) objs[1];
                stopTime = (Date) objs[2];
                color = (String) objs[3];

                if (startTime.before(begin)) {// 起始时间超出时间选择的开始范围，则将其设置为开始范围
                    startTime = begin;
                }
                if (stopTime == null) {// 结束时间为空，说明该状态还在持续，则将其设置为当前时间
                    stopTime = new Date();
                }
                if (stopTime.after(end)) {// 当前状态持续时间超过当前时间选择的范围
                    stopTime = end;
                }

                tmp = new MechineStopInformationBean(null, machineName, startTime, stopTime, color);
                result.add(tmp);
            }
        }

        return result;
    }

    /**
     * 根据起始间隔，获得故障停机时间对象的柱状图宽度
     * 
     * @param data
     *            : 机台停机信息
     */
    private Map<String, List<TimeChooseStatusBean>> processingDataOfMechineStop(List<MechineStopInformationBean> data,
            Date begin, Date end, Integer width) {
        // 获取起始时间与结束时间相差分钟数
        long seconds = TimeUtils.getDisparityOfSecond(begin, end);
        // 获取一分钟对应的像素宽度
        double widthOfMinuts = (double) width / (double) seconds;// 每秒的宽度
        widthOfMinuts = MathUtils.formatTwoBit(widthOfMinuts);// 控制两位精度
        // 故障的持续分钟数
        Date endTime = null;
        Date beginTime = null;
        long duration = -1;// 持续秒数
        // 故障持续的宽度
        double wid = 0;
        List<TimeChooseStatusBean> bottleWashingMachine = new ArrayList<>();
        List<TimeChooseStatusBean> fillingMachine = new ArrayList<>();
        List<TimeChooseStatusBean> sterilizationMachine = new ArrayList<>();
        List<TimeChooseStatusBean> labelingMachine = new ArrayList<>();
        List<TimeChooseStatusBean> lianDao1 = new ArrayList<>();
        List<TimeChooseStatusBean> lianDao4 = new ArrayList<>();
        List<TimeChooseStatusBean> lianDao2 = new ArrayList<>();
        List<TimeChooseStatusBean> lianDao3 = new ArrayList<>();
        for (MechineStopInformationBean m : data) {
            // 按机台分类
            if (m.getMechineName().equals(Machines.BOTTLEWASHINGMACHINE.getName())) {
                // 洗瓶机
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                bottleWashingMachine.add(new TimeChooseStatusBean(m.getColor(), wid));
            } else if (m.getMechineName().equals(Machines.LIANDAO1.getName())) {
                // 洗瓶机至灌装机链道
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                lianDao1.add(new TimeChooseStatusBean(m.getColor(), wid));
            } else if (m.getMechineName().equals(Machines.LIANDAO4.getName())) {
                // EBI至灌装机
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                lianDao4.add(new TimeChooseStatusBean(m.getColor(), wid));
            } else if (m.getMechineName().equals(Machines.FILLINGMACHINE.getName())) {
                // 灌装机
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                fillingMachine.add(new TimeChooseStatusBean(m.getColor(), wid));
            } else if (m.getMechineName().equals(Machines.LIANDAO2.getName())) {
                // 灌装机至杀菌机链道
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                lianDao2.add(new TimeChooseStatusBean(m.getColor(), wid));
            } else if (m.getMechineName().equals(Machines.STERILIZATIONMACHINE.getName())) {
                // 杀菌机
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                sterilizationMachine.add(new TimeChooseStatusBean(m.getColor(), wid));
            } else if (m.getMechineName().equals(Machines.LIANDAO3.getName())) {
                // 杀菌机至贴标机链道
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                lianDao3.add(new TimeChooseStatusBean(m.getColor(), wid));
            } else if (m.getMechineName().equals(Machines.LABELINGMACHINE.getName())) {
                // 贴标机
                // 获取当前故障的持续分钟数
                beginTime = m.getBeginTime();
                endTime = m.getEndTime();
                duration = TimeUtils.getDisparityOfSecond(beginTime, endTime);
                // 计算当前故障持续的宽度
                wid = duration * widthOfMinuts;
                labelingMachine.add(new TimeChooseStatusBean(m.getColor(), wid));
            }
        }
        Map<String, List<TimeChooseStatusBean>> result = new HashMap<>();
        result.put("bottleWashingMachine", bottleWashingMachine);
        result.put("fillingMachine", fillingMachine);
        result.put("sterilizationMachine", sterilizationMachine);
        result.put("labelingMachine", labelingMachine);
        result.put("lianDao1", lianDao1);
        result.put("lianDao2", lianDao2);
        result.put("lianDao3", lianDao3);
        result.put("lianDao4", lianDao4);

        return result;
    }
}
