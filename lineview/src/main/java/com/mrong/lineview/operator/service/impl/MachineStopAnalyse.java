package com.mrong.lineview.operator.service.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mrong.lineview.common.entity.RawDataBean;
import com.mrong.lineview.common.entity.ResultDataBean;
import com.mrong.lineview.operator.dao.ITimeChooseDao;
import com.mrong.lineview.operator.entity.MachineSelfResultInfo;
import com.mrong.lineview.operator.entity.MechineStopInformationBean;
import com.mrong.lineview.util.Colors;
import com.mrong.lineview.util.Machines;

/**
 * 判断机台故障逻辑以及颜色
 * 
 * @author 林金成
 */
// @SuppressWarnings("all")
public class MachineStopAnalyse {

    private ResultDataBean resultDataBean;
    private ResultDataBean oldResultDataBean;
    private RawDataBean rawDataBean;
    //    private RawDataBean oldRawDataBean;

    private ITimeChooseDao timeChooseDao;

    public void setTimeChooseDao(ITimeChooseDao timeChooseDao) {
        this.timeChooseDao = timeChooseDao;
    }

    private AnalysFillingMachine analysFillingMachine;

    public void setAnalysFillingMachine(AnalysFillingMachine analysFillingMachine) {
        this.analysFillingMachine = analysFillingMachine;
    }

    /**
     * 根据生产线机台状态，分析机台停机原因逻辑
     * 
     * @param resultDataBean
     *            : 保存生产线机台信息的实体类
     * @return : 返回处理过后的实体类
     */
    public ResultDataBean mechineStopAnalyse(ResultDataBean resultDataBean, ResultDataBean oldResultDataBean,
            RawDataBean rawDataBean, RawDataBean oldRawDataBean) {

        this.resultDataBean = resultDataBean;
        this.rawDataBean = rawDataBean;
        this.oldResultDataBean = oldResultDataBean;
        //        this.oldRawDataBean = oldRawDataBean;

        // 分析各机台自身原因
        this.analyseColorRedOfMechine();

        // 分析各机台之间的影响
        this.analyseColorBlueOfMechine();

        // 判断止瓶器信号，若打开则视为灌装机停机
        this.resultDataBean = analysFillingMachine.analyseFillingMachine(this.resultDataBean, this.rawDataBean);

        // 当机台状态发生改变时，保存机台的改变信息
        this.saveIfStatusChange();

        // 当机台出现故障，保存故障信息
        this.saveIfMachineHasFault();

        //        // 统计截止到当前时间四大机台大小停机的次数和持续时间
        //        this.analyseMachineStopInfomation();

        return resultDataBean;
    }

    /**
     * 当机台故障出现时，保存当前故障到数据库
     */
    private void saveIfMachineHasFault() {
        saveIfMachineHasFault("BottleWasher", Machines.BOTTLEWASHINGMACHINE.getName());
        saveIfMachineHasFault("LianDao1", Machines.LIANDAO1.getName());
        saveIfMachineHasFault("LianDao4", Machines.LIANDAO4.getName());
        saveIfMachineHasFault("FillingMachine", Machines.FILLINGMACHINE.getName());
        saveIfMachineHasFault("LianDao2", Machines.LIANDAO2.getName());
        saveIfMachineHasFault("SterilizationMachine", Machines.STERILIZATIONMACHINE.getName());
        saveIfMachineHasFault("LianDao3", Machines.LIANDAO3.getName());
        saveIfMachineHasFault("LabelingMachine", Machines.LABELINGMACHINE.getName());
    }

    /**
     * 当一台机台出现问题，保存当前故障信息到数据库
     * 
     * @param engName
     * @param chiName
     */
    @SuppressWarnings("unchecked")
    private void saveIfMachineHasFault(String engName, String chiName) {
        Class<?> clazz = ResultDataBean.class;
        String sql = null;
        Map<String, Object> propertyMap = new HashMap<>();
        try {
            // 获取机台故障列表
            Method getReason = clazz.getMethod("get" + engName + "_reason");
            List<String> oldReason = (List<String>) getReason.invoke(oldResultDataBean);
            List<String> newReason = (List<String>) getReason.invoke(resultDataBean);

            // 遍历faultInfomation中存储的已经发生的故障，
            if (oldReason != null) {
                for (String str : oldReason) {
                    // 如果现在的故障中没有了其中的故障，说明该故障已经消失，则将该故障的结束时间信息更新到数据库
                    if (((newReason != null) && !newReason.contains(str)) || (newReason == null)) {
                        sql = "update mechine_selfresult_information set end_time = NOW() where id=(select max(id)  from ( select id from mechine_selfresult_information where machine_name= :machineName and result_info =:resultInfo) as temp)";
                        propertyMap.put("machineName", chiName);
                        propertyMap.put("resultInfo", str);
                        timeChooseDao.executeBySql(sql, propertyMap);
                    }
                }
            }

            if (newReason != null) {
                for (String str : newReason) {
                    // 如果上一次的故障中没有该条，说明该故障刚触发，记录
                    if (((oldReason != null) && !oldReason.contains(str)) || (oldReason == null)) {
                        timeChooseDao.save(new MachineSelfResultInfo(null, chiName, str, new Date(), null, new Date()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当机台状态发生改变时，保存机台的改变信息
     */
    private void saveIfStatusChange() {
        saveIfStatusChange("BottleWasher", Machines.BOTTLEWASHINGMACHINE.getName());
        saveIfStatusChange("LianDao1", Machines.LIANDAO1.getName());
        saveIfStatusChange("LianDao4", Machines.LIANDAO4.getName());
        saveIfStatusChange("FillingMachine", Machines.FILLINGMACHINE.getName());
        saveIfStatusChange("LianDao2", Machines.LIANDAO2.getName());
        saveIfStatusChange("SterilizationMachine", Machines.STERILIZATIONMACHINE.getName());
        saveIfStatusChange("LianDao3", Machines.LIANDAO3.getName());
        saveIfStatusChange("LabelingMachine", Machines.LABELINGMACHINE.getName());
    }

    /**
     * 分析蓝条位置
     * 
     * @param resultDataBean
     * @return
     */
    private void analyseColorBlueOfMechine() {
        analyseSterilizationMachine();
        analyseLabelingMachine();
    }

    //    /**
    //     * 统计大停机、小停机次数和时间
    //     */
    //    private void analyseMachineStopInfomation() {
    //        // 洗瓶机的大小停机信息
    //        resultDataBean.setStopInfomationOfBottleWasher(
    //                new TimesOfMachineStop(getBigMachineStopTimes(rawDataBean.getStopInfomationOfBottleWasher()),
    //                        getSmallMachineStopTimes(rawDataBean.getStopInfomationOfBottleWasher()),
    //                        getDurationOfBigMachineStop(rawDataBean.getStopInfomationOfBottleWasher()),
    //                        getDurationOfSmallMachineStop(rawDataBean.getStopInfomationOfBottleWasher())));
    //        // 灌装机大小停机信息
    //        resultDataBean.setStopInfomationOfFillingMachine(
    //                new TimesOfMachineStop(getBigMachineStopTimes(rawDataBean.getStopInfomationOfFillingMachine()),
    //                        getSmallMachineStopTimes(rawDataBean.getStopInfomationOfFillingMachine()),
    //                        getDurationOfBigMachineStop(rawDataBean.getStopInfomationOfFillingMachine()),
    //                        getDurationOfSmallMachineStop(rawDataBean.getStopInfomationOfFillingMachine())));
    //        // 杀菌机大小停机信息
    //        resultDataBean.setStopInfomationOfSterilizationMachine(
    //                new TimesOfMachineStop(getBigMachineStopTimes(rawDataBean.getStopInfomationOfSterilizationMachine()),
    //                        getSmallMachineStopTimes(rawDataBean.getStopInfomationOfSterilizationMachine()),
    //                        getDurationOfBigMachineStop(rawDataBean.getStopInfomationOfSterilizationMachine()),
    //                        getDurationOfSmallMachineStop(rawDataBean.getStopInfomationOfSterilizationMachine())));
    //        // 贴标机大小停机信息
    //        resultDataBean.setStopInfomationOfLabelingMachine(
    //                new TimesOfMachineStop(getBigMachineStopTimes(rawDataBean.getStopInfomationOfLabelingMachine()),
    //                        getSmallMachineStopTimes(rawDataBean.getStopInfomationOfLabelingMachine()),
    //                        getDurationOfBigMachineStop(rawDataBean.getStopInfomationOfLabelingMachine()),
    //                        getDurationOfSmallMachineStop(rawDataBean.getStopInfomationOfLabelingMachine())));
    //    }
    //
    //    /**
    //     * 获取大停机次数
    //     * 
    //     * @param list
    //     *            : 停机信息的集合
    //     * @return
    //     */
    //    private int getBigMachineStopTimes(List<MechineStopInformationBean> list) {
    //        int result = 0;
    //        int duration = 0;
    //        if (list != null) {
    //            for (MechineStopInformationBean m : list) {
    //                duration = TimeUtils.getDisparityOfMinuts(m.getBeginTime(), m.getEndTime());
    //                if (duration >= 5) {
    //                    result += 1;
    //                }
    //            }
    //        }
    //        return result;
    //    }
    //
    //    /**
    //     * 获取小停机次数
    //     * 
    //     * @param list
    //     *            : 停机信息的集合
    //     * @return
    //     */
    //    private int getSmallMachineStopTimes(List<MechineStopInformationBean> list) {
    //        int result = 0;
    //        int duration = 0;
    //        if (list != null) {
    //            for (MechineStopInformationBean m : list) {
    //                duration = TimeUtils.getDisparityOfMinuts(m.getBeginTime(), m.getEndTime());
    //                if (duration < 5) {
    //                    result += 1;
    //                }
    //            }
    //        }
    //        return result;
    //    }
    //
    //    /**
    //     * 获取大停机的持续时间(分钟)
    //     * 
    //     * @param list
    //     *            : 停机信息的集合
    //     * @return
    //     */
    //    private int getDurationOfBigMachineStop(List<MechineStopInformationBean> list) {
    //        int result = 0;
    //        int duration = 0;
    //        if (list != null) {
    //            for (MechineStopInformationBean m : list) {
    //                duration = TimeUtils.getDisparityOfMinuts(m.getBeginTime(), m.getEndTime());
    //                if (duration >= 5) {
    //                    result += duration;
    //                }
    //            }
    //        }
    //        return result;
    //    }
    //
    //    /**
    //     * 获取小停机的持续时间
    //     * 
    //     * @param list
    //     *            : 停机信息的集合
    //     * @return
    //     */
    //    private int getDurationOfSmallMachineStop(List<MechineStopInformationBean> list) {
    //        int result = 0;
    //        int duration = 0;
    //        if (list != null) {
    //            for (MechineStopInformationBean m : list) {
    //                duration = TimeUtils.getDisparityOfMinuts(m.getBeginTime(), m.getEndTime());
    //                if (duration < 5) {
    //                    result += duration;
    //                }
    //            }
    //        }
    //        return result;
    //    }

    /**
     * 当机台状态发生变化是，记录到数据库
     * 
     * @param engName
     *            : 机台字段名
     * @param chiName
     *            : 机台中文名
     */
    private void saveIfStatusChange(String engName, String chiName) {
        // Class<?> clazz1 = RawDataBean.class;
        Class<?> clazz2 = ResultDataBean.class;
        try {
            // Method getStatus = clazz1.getMethod("get" + engName + "_status");
            Method getColors = clazz2.getMethod("get" + engName + "_color");
            // Integer oldStatus = (Integer) getStatus.invoke(oldRawDataBean);
            // Integer newStatus = (int) getStatus.invoke(rawDataBean);
            String oldColor = (String) getColors.invoke(oldResultDataBean);
            String newColor = (String) getColors.invoke(resultDataBean);
            if (oldColor == null || "".equals(oldColor)) {
                String sql = "insert into mechine_stop_information values(null, :machineName, :begin, null, :color)";
                Map<String, Object> propertyMap = new HashMap<>();
                propertyMap.put("machineName", chiName);
                propertyMap.put("begin", new Date());
                propertyMap.put("color", (String) getColors.invoke(resultDataBean));
                timeChooseDao.executeBySql(sql, propertyMap);
                /* timeChooseDao.save(new MechineStopInformationBean(null, chiName, new Date(), null,
                 * (String) getColors.invoke(resultDataBean))); */
            } else {
                if (!newColor.equals(oldColor)) {
                    String sql = "update mechine_stop_information set end_time = NOW() where id=(select max(id)  from ( select id from mechine_stop_information where machine_name= :machineName ) as temp)";
                    Map<String, Object> propertyMap = new HashMap<>();
                    propertyMap.put("machineName", chiName);
                    // 修改该机台的上一台记录，添加结束时间
                    timeChooseDao.executeBySql(sql, propertyMap);
                    timeChooseDao.save(new MechineStopInformationBean(null, chiName, new Date(), null,
                            (String) getColors.invoke(resultDataBean)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分析杀菌机
     */
    private void analyseSterilizationMachine() {
        // 判断杀菌机后堵是否触发
        Map<String, Integer> map = rawDataBean.getSterilizationMachine_point();
        if (map != null) {
            int a = map.get("上、下层堵瓶标志");
            if (a == 0) {
                resultDataBean.setSterilizationMachine_color(Colors.MAZARINE.getName());
            }
        }
    }

    /**
     * 分析杀菌机至贴标机链道
     */

    /**
     * 分析贴标机
     */
    private void analyseLabelingMachine() {
        // 判断贴标机是否后堵
        Map<String, Integer> map = rawDataBean.getLabelingMachine_point();
        if (map != null) {
            int a = map.get("出瓶堵塞");
            if (a == 0) {
                resultDataBean.setLabelingMachine_color(Colors.MAZARINE.getName());
            }
        }
    }

    /**
     * 分析红条
     * 
     * @param rawDataBean
     *            : 原始数据
     * @param resultDataBean
     *            : 结果数据
     * @return
     */
    /* private void analyseColorRedOfMechine() {
     * resultDataBean.setLianDao7_reason(getMechineStopReason(rawDataBean.getLianDao7_point()));
     * if (resultDataBean.getLianDao7_reason() != null) {
     * resultDataBean.setLianDao7_color(Colors.RED.getName());
     * }
     * } */

    /**
     * 分析红条
     */
    private void analyseColorRedOfMechine() {
        analyseBottleWasher();// 洗瓶机
        analyseBottleWasherToEBI();// 洗瓶机至EBI链道
        analyseEBIToFillingMachine();// EBI至灌装机链道
        analyseFillingMachine();// 灌装机
        analyseFillingMachineToSterilizationMachine();// 灌装机至杀菌机链道
        analyseSterilizationMachineOfRed();// 杀菌机
        analyseSterilizationMachineToLabelingMachine();// 杀菌机至贴标机链道
        analyseLabelingMachineOfRed();// 贴标机
    }

    /**
     * 贴标机红条分析
     */
    private void analyseLabelingMachineOfRed() {
        Map<String, Integer> points = rawDataBean.getLabelingMachine_point();
        resultDataBean.setLabelingMachine_reason(this.getMechineStopReason(points));
        List<String> list = new ArrayList<>();
        list.add("出瓶堵塞");

        boolean b = false;
        for (String key : points.keySet()) {
            if (!list.contains(key)) {
                Integer value = points.get(key);
                if (value == 0) {
                    b = true;
                }
            }
        }

        resultDataBean.setLabelingMachine_color(b ? Colors.RED.getName() : Colors.GREEN.getName());
        if (rawDataBean.getLabelingMachine_status() == 0) {
            resultDataBean.setLabelingMachine_color(Colors.RED.getName());
        }
    }

    /**
     * 杀菌机至贴标机链道
     */
    private void analyseSterilizationMachineToLabelingMachine() {
        Map<String, Integer> points = rawDataBean.getLianDao3_point();
        resultDataBean.setLianDao3_reason(this.getMechineStopReason(points));
        if (rawDataBean.getLianDao3_status() == 0) {
            resultDataBean.setLianDao3_color(Colors.RED.getName());
        } else {
            resultDataBean.setLianDao3_color(Colors.GREEN.getName());
        }
    }

    /**
     * 杀菌机红条分析
     */
    private void analyseSterilizationMachineOfRed() {
        Map<String, Integer> points = rawDataBean.getSterilizationMachine_point();
        resultDataBean.setSterilizationMachine_reason(this.getMechineStopReason(points));
        List<String> list = new ArrayList<>();
        list.add("上、下层堵瓶标志");

        boolean b = false;
        for (String key : points.keySet()) {
            Integer value = points.get(key);
            if (!list.contains(key)) {
                if (value == 0) {
                    b = true;
                }
            }
        }

        resultDataBean.setSterilizationMachine_color(b ? Colors.RED.getName() : Colors.GREEN.getName());
        if (rawDataBean.getSterilizationMachine_status() == 0) {
            resultDataBean.setSterilizationMachine_color(Colors.RED.getName());
        }
    }

    /**
     * 灌装机至杀菌机链道红条分析
     */
    private void analyseFillingMachineToSterilizationMachine() {
        Map<String, Integer> points = rawDataBean.getLianDao2_point();
        resultDataBean.setLianDao2_reason(this.getMechineStopReason(points));
        if (rawDataBean.getLianDao2_status() == 0) {
            resultDataBean.setLianDao2_color(Colors.RED.getName());
        } else {
            resultDataBean.setLianDao2_color(Colors.GREEN.getName());
        }
    }

    /**
     * 灌装机红条分析
     */
    private void analyseFillingMachine() {
        Map<String, Integer> points = rawDataBean.getFillingMachine_point();
        resultDataBean.setFillingMachine_reason(this.getMechineStopReason(points));
        List<String> list = new ArrayList<>();
        list.add("入口缺瓶");
        list.add("远处后堵");
        list.add("止瓶器开");
        list.add("入口倒瓶");

        boolean b = false;
        for (String key : points.keySet()) {
            Integer value = points.get(key);
            if (!list.contains(key)) {// key不在list集合中，再进行判断
                if (value == 0) {
                    b = true;
                }
            }
        }

        resultDataBean.setFillingMachine_color(b ? Colors.RED.getName() : Colors.GREEN.getName());
        if (rawDataBean.getFillingMachine_status() == 0) {
            resultDataBean.setFillingMachine_color(Colors.RED.getName());
        }
    }

    /**
     * EBI至灌装机链道红条分析
     */
    private void analyseEBIToFillingMachine() {
        Map<String, Integer> points = rawDataBean.getLianDao4_point();
        resultDataBean.setLianDao4_reason(this.getMechineStopReason(points));
        if (rawDataBean.getLianDao4_status() == 0) {
            resultDataBean.setLianDao4_color(Colors.RED.getName());
        } else {
            resultDataBean.setLianDao4_color(Colors.GREEN.getName());
        }
    }

    /**
     * 洗瓶机至EBI链道红条分析
     */
    private void analyseBottleWasherToEBI() {
        Map<String, Integer> points = rawDataBean.getLianDao1_point();
        resultDataBean.setLianDao1_reason(this.getMechineStopReason(points));
        if (rawDataBean.getLianDao1_status() == 0) {
            resultDataBean.setLianDao1_color(Colors.RED.getName());
        } else {
            resultDataBean.setLianDao1_color(Colors.GREEN.getName());
        }
    }

    /**
     * 洗瓶机红条分析
     */
    private void analyseBottleWasher() {
        Map<String, Integer> points = rawDataBean.getBottleWasher_point();
        resultDataBean.setBottleWasher_reason(this.getMechineStopReason(points));
        boolean b = false;
        for (String key : points.keySet()) {
            Integer value = points.get(key);
            if (value == 0) {
                b = true;
            }
        }

        resultDataBean.setBottleWasher_color(b ? Colors.RED.getName() : Colors.GREEN.getName());
        if (rawDataBean.getBottleWasher_status() == 0) {
            resultDataBean.setBottleWasher_color(Colors.RED.getName());
        }
    }

    /* // 获得初始数据类型的所有声明方法
     * Method[] rawDataBeanMethods = rawDataBean.getClass().getDeclaredMethods();
     * // 根据返回值的类型（只有故障点的get方法才返回的是map集合）获取其中故障点的所有get方法
     * Method[] points = new Method[8];
     * Method[] status = new Method[8];
     * Map<String, Integer> map = null;
     * List<String> list = null;
     * try {
     * // 获取所有返回值为Map的方法（故障点属性的get方法）
     * int i = 0;
     * int j = 0;
     * for (Method m : rawDataBeanMethods) {
     * String[] strs = m.getName().split("_");
     * Class<?> clazz = m.getReturnType();
     * if (("java.util.Map".equals(clazz.getName())) && (strs.length > 1)) {
     * points[i] = m;
     * i++;
     * }
     * if (("java.lang.Integer".equals(clazz.getName())) && (strs.length > 1)) {
     * status[j] = m;
     * j++;
     * }
     * }
     * for (Method method : points) {
     * // 根据‘_’分割方法名，获得属性名，例如：getBottleWasher_point，分割后获得其属性名BottleWasher，后面拼接其set方法时用
     * String[] strs = method.getName().split("_");
     * String filedName = strs[0].substring(3, strs[0].length());
     * // 调用get方法获取故障点map集合
     * map = (Map<String, Integer>) method.invoke(rawDataBean);
     * // 根据故障点数据分析其是否停机以及停机原因
     * list = this.getMechineStopReason(map);
     * // 利用反射调用setBottleWasher_reason方法设置其停机原因
     * resultDataBean.getClass().getMethod("set" + filedName + "_reason", List.class).invoke(resultDataBean,
     * list);
     * // 如果停机原因不为空，则利用反射调用setBottleWasher_color方法将其颜色设置为红色
     * if (!"LianDao".equals(filedName.substring(0, 7))) {
     * if (list != null && list.size() > 0) {
     * resultDataBean.getClass().getMethod("set" + filedName + "_color", String.class)
     * .invoke(resultDataBean, Colors.RED.getName());
     * } else {
     * resultDataBean.getClass().getMethod("set" + filedName + "_color", String.class)
     * .invoke(resultDataBean, Colors.GREEN.getName());
     * }
     * }
     * }
     * for (Method method : status) {
     * // 根据‘_’分割方法名，获得属性名，例如：getBottleWasher_point，分割后获得其属性名BottleWasher，后面拼接其set方法时用
     * String[] strs = method.getName().split("_");
     * String filedName = strs[0].substring(3, strs[0].length());
     * Integer m = (Integer) method.invoke(rawDataBean);
     * if (m != null && m == 0) {
     * resultDataBean.getClass().getMethod("set" + filedName + "_color", String.class)
     * .invoke(resultDataBean, Colors.RED.getName());
     * } else {
     * if ("LianDao".equals(filedName.substring(0, 7))) {
     * resultDataBean.getClass().getMethod("set" + filedName + "_color", String.class)
     * .invoke(resultDataBean, Colors.GREEN.getName());
     * }
     * }
     * }
     * } catch (Exception e) {
     * System.out
     * .println("------------------------------------>分析红条的方法抛出异常<-------------------------------------");
     * e.printStackTrace();
     * }
     * } */

    /**
     * 根据传入的机台故障监测点数据，排查是否出现故障，记录并返回原因
     * 
     * @param data
     *            : 存放相应机台故障监测点0/1信号的 Map<故障名, 开关量>集合
     * @return : 机台触发故障点的集合
     */
    private List<String> getMechineStopReason(Map<String, Integer> data) {
        List<String> result = null;
        if (data != null) {
            Set<String> keys = data.keySet();
            result = new ArrayList<String>();
            for (String key : keys) {
                int m = data.get(key);
                if (m == 0) {
                    result.add(key);
                }
            }
        }
        return result;
    }
}
