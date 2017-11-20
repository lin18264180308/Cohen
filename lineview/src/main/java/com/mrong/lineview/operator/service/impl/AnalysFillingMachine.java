package com.mrong.lineview.operator.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.entity.RawDataBean;
import com.mrong.lineview.common.entity.ResultDataBean;
import com.mrong.lineview.operator.dao.IMachineStopFrequencyDao;
import com.mrong.lineview.util.Colors;
import com.mrong.lineview.util.Machines;

/**
 * 酒机后故障逻辑分析
 * 
 * @author 林金成 2017年10月25日
 */
public class AnalysFillingMachine {

    private ResultDataBean resultDataBean;
    private RawDataBean rawDataBean;
    private IMachineStopFrequencyDao machineStopFrequencyDao;

    public void setMachineStopFrequencyDao(IMachineStopFrequencyDao machineStopFrequencyDao) {
        this.machineStopFrequencyDao = machineStopFrequencyDao;
    }

    public ResultDataBean getResultDataBean() {
        if (resultDataBean == null) {
            resultDataBean = new ResultDataBean();
        }

        return resultDataBean;
    }

    public RawDataBean getRawDataBean() {
        if (rawDataBean == null) {
            rawDataBean = new RawDataBean();
        }

        return rawDataBean;
    }

    /**
     * 分析酒机的情况
     * 
     * @param resultDataBean
     *            : 处理后的结果数据
     * @param rawDataBean
     *            : 处理前的原始数据
     */
    public ResultDataBean analyseFillingMachine(ResultDataBean resultDataBean, RawDataBean rawDataBean) {
        this.rawDataBean = rawDataBean;
        this.resultDataBean = resultDataBean;

        this.analyseBottleStopper();
        return resultDataBean;
    }

    /**
     * 先判断酒机是否停机
     */
    private void analyseBottleStopper() {
        if (rawDataBean.getBottleStopper() == 0 || rawDataBean.getFillingMachine_status() == 0) {// 判断止瓶器信号，为0打开不进瓶
            Map<String, Integer> fillingMachinePoints = rawDataBean.getFillingMachine_point();
            // 排除前缺后堵信号
            int before = fillingMachinePoints.get("入口缺瓶");// 酒机前缺信号
            int after = fillingMachinePoints.get("远处后堵");// 酒机后堵信号
            // 判断酒机自身故障时，排除前缺后堵信号不参与故障判断
            List<String> list = new ArrayList<>();
            list.add("入口缺瓶");
            list.add("远处后堵");
            list.add("止瓶器开");
            list.add("入口倒瓶");
            for (String key : fillingMachinePoints.keySet()) {
                if (!list.contains(key)) {
                    if (fillingMachinePoints.get(key) == 0) {// 酒机自身出现问题，酒机为红色
                        resultDataBean.setFillingMachine_color(Colors.RED.getName());
                        return;// 若酒机出现故障，f为true
                    } else {
                        resultDataBean.setFillingMachine_color(Colors.GREEN.getName());
                    }
                }
            }

            // 当酒机没有自身故障时
            if ((after == 0 && before == 0) || (after == 0)) {// 前缺后堵同时触发
                // 后堵
                resultDataBean.setFillingMachine_color(Colors.MAZARINE.getName());
                this.analyseBackOfFillingMachine();
            } else if (before == 0) {// 只触发前缺
                // 前缺
                resultDataBean.setFillingMachine_color(Colors.WATHET.getName());
                this.analyseFrontOfFillingMachine();
            } else {// 都没触发
                resultDataBean.setFillingMachine_color(Colors.RED.getName());
                // TODO 酒机其他原因
            }
        } else {// 没停机
            return;
        }
    }

    /**
     * 酒机前缺，向前分析
     */
    private void analyseFrontOfFillingMachine() {
        // I40_0:酒机前缺瓶信号是否触发为0
        int I40_0 = rawDataBean.getFillingMachine_point().get("入口倒瓶");
        // 酒机前缺瓶信号是触发
        if (I40_0 == 1) {// 没触发
            // 判断酒机EBI是否停机(M26)1为开，0为关
            Integer M26 = rawDataBean.getLianDao4_point().get("M26");
            if (M26 == 0) {
                // 白条显示在EBI至酒机的输送带上
                resultDataBean.setLianDao4_color(Colors.WHITE.getName());
            } else {
                // TODO 前20秒内EBI前无压力堵瓶信号是否触发
                Date endTime = new Date();
                long milliseconds = endTime.getTime() - 20 * 1000;
                Date beginTime = new Date(milliseconds);
                boolean befferEBI = machineStopFrequencyDao.getMachineFaultDuration("I2.7", beginTime, endTime);
                if (befferEBI) {
                    // 白条出现在洗瓶机至EBI的输送带上
                    resultDataBean.setLianDao1_color(Colors.WHITE.getName());
                } else {
                    // TODO 前15分钟内洗瓶机是否出现累积超过3分钟的停机
                    endTime = new Date();
                    milliseconds = endTime.getTime() - 15 * 60 * 1000;
                    beginTime = new Date(milliseconds);
                    // 未停机的时间
                    double duration = machineStopFrequencyDao.getMachineStopFrequency(beginTime, endTime,
                            Machines.BOTTLEWASHINGMACHINE.getName(), Colors.RED.getName());
                    duration = duration + machineStopFrequencyDao.getMachineStopFrequency(beginTime, endTime,
                            Machines.BOTTLEWASHINGMACHINE.getName(), Colors.MAZARINE.getName());
                    duration = duration + machineStopFrequencyDao.getMachineStopFrequency(beginTime, endTime,
                            Machines.BOTTLEWASHINGMACHINE.getName(), Colors.WATHET.getName());
                    Integer overthree = 0;
                    if (duration > 3) {// 15分钟内的停机时间是否大于3分钟
                        overthree = 1;
                    } else {// 不足3分钟
                        overthree = 0;
                    }
                    if (overthree == 1) {
                        // 白条出现在洗瓶机上
                        resultDataBean.setBottleWasher_color(Colors.WHITE.getName());
                    } else {
                        // 白条出现在洗瓶机至EBI输送带上
                        resultDataBean.setLianDao1_color(Colors.WHITE.getName());
                    }
                }
            }
        } else {// I40.0触发、白条出现在EBI至灌装机链道上
            resultDataBean.setLianDao4_color(Colors.WHITE.getName());
        }
    }

    /**
     * 酒机后堵
     */
    private void analyseBackOfFillingMachine() {
        // TODO 前5分钟内杀菌机是否存在由后堵故障端触发的停机
        Date endTime = new Date();
        long milliseconds = endTime.getTime() - 5 * 60 * 1000;
        Date beginTime = new Date(milliseconds);
        boolean b = machineStopFrequencyDao.getMachineFaultDuration("上、下层堵瓶标志", beginTime, endTime);
        // 如果前5分钟内杀菌机存在由后堵故障端触发的停机
        if (b) {
            endTime = new Date();
            milliseconds = endTime.getTime() - 15 * 60 * 1000;
            beginTime = new Date(milliseconds);
            double duration = machineStopFrequencyDao.getMachineStopFrequency(beginTime, endTime,
                    Machines.STERILIZATIONMACHINE.getName(), Colors.GREEN.getName());
            Integer c = 0;
            if ((15 - duration) > 3) {// 除去正常运行的停机时间，超过3分钟
                c = 1;
            } else {// 未超过3分钟
                c = 0;
            }
            // TODO 前15分钟内贴标机是否存在累积超过3分钟停机时间
            if (c == 1) {
                // TODO 如果超过3分钟
                // 贴标机导致管酒机停机,白条出现在贴标机状态栏
                // 贴标机自身故障点触发,贴标机白条
                resultDataBean.setLabelingMachine_color(Colors.WHITE.getName());
            } else {
                // TODO 如果没超过
                // 白条出现在杀菌机至贴标机的输送带上
                // 原因在输送带上
                resultDataBean.setLianDao3_color(Colors.WHITE.getName());
            }
        } else {
            // TODO 前5分钟内杀菌机是否由内部故障点触发停机
            endTime = new Date();
            milliseconds = endTime.getTime() - 5 * 60 * 1000;
            beginTime = new Date(milliseconds);
            double duration = machineStopFrequencyDao.getMachineStopFrequency(beginTime, endTime,
                    Machines.STERILIZATIONMACHINE.getName(), Colors.RED.getName());
            Integer c = 0;
            if (duration > 0) {// 前5分钟内杀菌机是否由内部故障点触发停机
                c = 1;
            } else {// 没触发
                c = 0;
            }
            if (c == 1) {
                // 触发
                // 杀菌机内部原因导致停机，白条出现在杀菌机状态栏，并显示相应的故障信息
                // 原因在输送带上
                resultDataBean.setSterilizationMachine_color(Colors.WHITE.getName());
            } else {
                // TODO 杀菌机其他原因导致停机，包括人工操作等，白条出现在杀菌机状态栏
                // 若有杀菌机故障点触发，杀菌机的问题，白条
                resultDataBean.setSterilizationMachine_color(Colors.WHITE.getName());
            }
        }
    }
}
