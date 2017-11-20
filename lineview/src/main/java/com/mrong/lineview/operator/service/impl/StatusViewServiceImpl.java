package com.mrong.lineview.operator.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import com.mrong.lineview.admin.service.PerformanceCurveService;
import com.mrong.lineview.common.entity.RawDataBean;
import com.mrong.lineview.common.entity.ResultDataBean;
import com.mrong.lineview.common.entity.TimeBean;
import com.mrong.lineview.operator.dao.IStatusViewDao;
import com.mrong.lineview.operator.entity.TimeChooseParameterBean;
import com.mrong.lineview.operator.service.IStatusViewService;
import com.mrong.lineview.util.BigTimeUtils;
import com.mrong.lineview.util.TimeUtils;

/**
 * 从数据库取数据
 * 
 * @author 林金成
 */
public class StatusViewServiceImpl implements IStatusViewService {

    /**
     * 当创建该对象时，加载机台和链道故障点到集合中
     */
    public StatusViewServiceImpl() {
        this.resultDataBean = new ResultDataBean();
        this.rawDataBean = new RawDataBean();
        this.timeBean = new TimeBean();
        initStopTime();
        // 获取当前项目根路径
        String path = StatusViewServiceImpl.class.getResource("/").getPath().replaceAll("%20", " ");
        String rootPath = path + "txt/";
        this.bottleWasherKeys = this.init(rootPath + "BottleWasherPoints.txt");
        this.fillingMachineKeys = this.init(rootPath + "FillingMechinePoints.txt");
        this.labelingMachineKeys = this.init(rootPath + "LabelingMachinePoints.txt");
        this.sterilizationMachineKeys = this.init(rootPath + "SterilizationMachinePoints.txt");
        this.lianDao1Keys = this.init(rootPath + "LianDao1.txt");
        this.lianDao2Keys = this.init(rootPath + "LianDao2.txt");
        this.lianDao3Keys = this.init(rootPath + "LianDao3.txt");
        this.artificialPoints = this.init(rootPath + "ArtificialPoints.txt");

        /**
         * 数据处理起始点，定时器
         */
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("-------->>>>>>>>>>>>>>>>>>-------------->定时器<------------<<<<<<<<<<<<<----------");
                // 分析
                analyseData();
            }
        }, 4000, 2500);

        Date start = BigTimeUtils.getCurrentDayEndTime();
        long period = 86400000;// 24小时的毫秒值
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 每天把绩效指标计算后存入新表
                ComputingPerformanceIndicators(start);
            }
        }, start, period);
    }

    /**
     * 酒机逻辑分析类
     */
    private MachineStopAnalyse machineStopAnalyse;
    /**
     * Dao层原子方法
     */
    private IStatusViewDao statusViewDao;
    /**
     * 处理前的原始数据
     */
    private RawDataBean rawDataBean;
    /**
     * 上一次处理前的原始数据
     */
    private RawDataBean oldRawDataBean;
    /**
     * 处理后的结果数据
     */
    private ResultDataBean resultDataBean;
    /**
     * 上一次处理前的结果数据
     */
    private ResultDataBean oldResultDataBean;
    /**
     * 模式持续时间，用于计算三大绩效指标
     */
    private TimeBean timeBean;
    /**
     * 
     */
    private TimeChooseParameterBean params;
    /**
     * 存放各大机台故障点的名称
     */
    private List<String> bottleWasherKeys;
    private List<String> fillingMachineKeys;
    private List<String> sterilizationMachineKeys;
    private List<String> labelingMachineKeys;
    private List<String> lianDao1Keys;
    private List<String> lianDao2Keys;
    private List<String> lianDao3Keys;
    private List<String> artificialPoints;

    private void analyseData() {
        this.oldRawDataBean = rawDataBean.getCloneObject();
        this.oldResultDataBean = resultDataBean.getCloneObject();
        // 获取plc中的机台状态信息
        this.getInfomationInDB();
        // 获取相应的时间
        this.getTime();
        // 判断机台启停状态及原因
        this.determineMachineStatus();
        //        // 获取当前班次的停机信息
        //        this.getStopInfomationInDB();
        // 获取当前时间四大机台的停机次数
        this.getStopTimes();
        // 获取四大机台的平均速度
        this.getAvgSpeed();
    }

    /**
     * 程序入口方法
     */
    public ResultDataBean getStatus() {
        return this.getResultDataBean();
    }

    /**
     * 获取当前时间四大机台的停机次数
     */
    private void getStopTimes() {
        /*-----------------------杀菌机故障点及次数---------------------------*/
        String sql = "select num from err_num";
        resultDataBean.setFaultTimesOfSterilizationMachine(
                statusViewDao.getStopTimesOfMachine(sterilizationMachineKeys, statusViewDao.getBySql(sql, null)));
        sql = "select num from err_num1";
        resultDataBean.setFaultTimesOfFillingMachine(
                statusViewDao.getStopTimesOfMachine(fillingMachineKeys, statusViewDao.getBySql(sql, null)));
        sql = "select num from err_num3";
        resultDataBean.setFaultTimesOfBottleWasher(
                statusViewDao.getStopTimesOfMachine(bottleWasherKeys, statusViewDao.getBySql(sql, null)));
        sql = "select num from err_num2";
        resultDataBean.setFaultTimesOfLabelingMachine(
                statusViewDao.getStopTimesOfMachine(labelingMachineKeys, statusViewDao.getBySql(sql, null)));
        /*-----------------------杀菌机故障点及次数---------------------------*/
    }

    /**
     * 获取四大机台的平均速度
     */
    private void getAvgSpeed() {
        Map<String, Object> propertyMap = new HashMap<>();
        Date begin = statusViewDao.getStartTime();
        Date end = new Date();
        propertyMap.put("startTime", begin);
        propertyMap.put("endTime", end);
        int duration = TimeUtils.getDisparityOfMinuts(begin, end);
        String sql = "select s1 from speed where sptime between :startTime and :endTime";
        resultDataBean.setAveSpeedOfBottleWasher(
                statusViewDao.getAvgSpeedOfMachine(statusViewDao.getBySql(sql, propertyMap), duration, 1.00));

        sql = "select s2 from speed where sptime between :startTime and :endTime";
        resultDataBean.setAveSpeedOfFillingMachine(
                statusViewDao.getAvgSpeedOfMachine(statusViewDao.getBySql(sql, propertyMap), duration, 1.64));

        sql = "select s3 from speed where sptime between :startTime and :endTime";
        resultDataBean.setAveSpeedOfSterilizationMachine(
                statusViewDao.getAvgSpeedOfMachine(statusViewDao.getBySql(sql, propertyMap), duration, 1000));

        sql = "select s4 from speed where sptime between :startTime and :endTime";
        resultDataBean.setAveSpeedOfLabelingMachine(
                statusViewDao.getAvgSpeedOfMachine(statusViewDao.getBySql(sql, propertyMap), duration, 1.00));
    }

    //    /**
    //     * 获取各机台一段时间停机状态以及时间
    //     */
    //    private void getStopInfomationInDB() {
    //        // 获取当前班次的起始时间
    //        Map<String, Object> propertyMap = new HashMap<>();
    //
    //        /* ----------------- 获取各机台一段时间停机状态以及时间 --begin---------------- */
    //        String sql = "select machine_name, begin_time, end_time, color from mechine_stop_information where  machine_name = :machineName and ( begin_time between :begin and NOW() ) and color != :color";
    //        propertyMap.clear();
    //        propertyMap.put("begin", statusViewDao.getStartTime());
    //        propertyMap.put("color", Colors.GREEN.getName());
    //        propertyMap.put("machineName", "洗瓶机");
    //        rawDataBean.setStopInfomationOfBottleWasher(statusViewDao.getStopInfomationOfMachine(sql, propertyMap));
    //
    //        propertyMap.put("machineName", "灌装机");
    //        rawDataBean.setStopInfomationOfFillingMachine(statusViewDao.getStopInfomationOfMachine(sql, propertyMap));
    //
    //        propertyMap.put("machineName", "杀菌机");
    //        rawDataBean.setStopInfomationOfSterilizationMachine(statusViewDao.getStopInfomationOfMachine(sql, propertyMap));
    //
    //        propertyMap.put("machineName", "贴标机");
    //        rawDataBean.setStopInfomationOfLabelingMachine(statusViewDao.getStopInfomationOfMachine(sql, propertyMap));
    //        /* ----------------- 获取各机台一段时间停机状态以及时间 --end---------------- */
    //    }

    /**
     * 获取有效生产时间 有效生产时间 = 总时间 - 非计划时间 - 计划停机时间 - 外部原因时间 - 内部原因时间
     */
    private void getTime() {
        /* ----------------- 获取模式切换的时间 --begin---------------- */
        // 获取模式切换的时间
        List<?> list = null;
        BigDecimal duration = null;
        String sql = "select sum(DURATION) from  mode_record where PATTERN_CLASSES= :modeClass";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("modeClass", "计划生产");
        list = statusViewDao.getBySql(sql, propertyMap);
        duration = (BigDecimal) list.get(0);// 持续时间
        if (duration != null) {
            this.getTimeBean().setTotalTime(duration.longValue());
        } else {
            this.getTimeBean().setTotalTime(0);
        }

        propertyMap.put("modeClass", "非计划生产");
        list = statusViewDao.getBySql(sql, propertyMap);
        duration = (BigDecimal) list.get(0);// 持续时间
        if (duration != null) {
            this.getTimeBean().setNotScheduledTime(duration.longValue());
        } else {
            this.getTimeBean().setNotScheduledTime(0);
        }

        propertyMap.put("modeClass", "计划停机");
        list = statusViewDao.getBySql(sql, propertyMap);
        duration = (BigDecimal) list.get(0);// 持续时间
        if (duration != null) {
            this.getTimeBean().setDownTimeOfPlannedActivities(duration.longValue());
        } else {
            this.getTimeBean().setDownTimeOfPlannedActivities(0);
        }

        propertyMap.put("modeClass", "外部原因");
        list = statusViewDao.getBySql(sql, propertyMap);
        duration = (BigDecimal) list.get(0);// 持续时间
        if (duration != null) {
            this.getTimeBean().setExternalCaused(duration.longValue());
        } else {
            this.getTimeBean().setExternalCaused(0);
        }

        propertyMap.put("modeClass", "内部原因");
        list = statusViewDao.getBySql(sql, propertyMap);
        duration = (BigDecimal) list.get(0);// 持续时间
        if (duration != null) {
            this.getTimeBean().setInternalCause(duration.longValue());
        } else {
            this.getTimeBean().setInternalCause(0);
        }

        /* ----------------- 获取模式切换的时间 --end---------------- */
    }

    /**
     * 从plc中查询基本信息
     */
    @SuppressWarnings("unchecked")
    private void getInfomationInDB() {
        String sql = null;
        /* ----------------- 读取故障点信息 --begin---------------- */
        // 洗瓶机故障点
        sql = "select * from Real_time_bottle_washing_machine_monitoring";
        rawDataBean.setBottleWasher_point(statusViewDao.getPointsOfMachine(sql, bottleWasherKeys));
        // 灌装机故障点
        sql = "select * from Real_time_filling_machine_monitoring";
        rawDataBean.setFillingMachine_point(statusViewDao.getPointsOfMachine(sql, fillingMachineKeys));
        // 杀菌机故障点
        sql = "select * from Real_time_sterilization_machine_monitoring";
        rawDataBean.setSterilizationMachine_point(statusViewDao.getPointsOfMachine(sql, sterilizationMachineKeys));
        // 贴标机故障点
        sql = "select * from Real_time_labeling_machine_monitoring";
        rawDataBean.setLabelingMachine_point(statusViewDao.getPointsOfMachine(sql, labelingMachineKeys));
        sql = "select * from Real_time_liandao_1";
        // 将链道1分为两段
        Map<String, Integer> map = statusViewDao.getPointsOfMachine(sql, lianDao1Keys);
        Integer m26 = map.get("M26");
        map.remove("M26");
        rawDataBean.setLianDao1_point(map);
        map = new HashMap<>();
        map.put("M26", m26);
        rawDataBean.setLianDao4_point(map);

        sql = "select * from Real_time_liandao_2";
        rawDataBean.setLianDao2_point(statusViewDao.getPointsOfMachine(sql, lianDao2Keys));
        sql = "select * from Real_time_liandao_3";
        rawDataBean.setLianDao3_point(statusViewDao.getPointsOfMachine(sql, lianDao3Keys));
        sql = "select * from Real_time_mc";
        rawDataBean.setManualSignal(statusViewDao.getPointsOfMachine(sql, artificialPoints));
        /* ----------------- 读取故障点信息 ---end----------------- */

        /* ----------------- 读取启停状态 ---begin---------------- */
        sql = "select bottle_washer, filling_mechine, sterilization_machine, labeling_machine from Real_time_four_machine";
        List<Object[]> objs = (List<Object[]>) statusViewDao.getBySql(sql, null);
        Object[] obj = objs.get(0);
        rawDataBean.setBottleWasher_status((boolean) obj[0] ? 1 : 0);
        rawDataBean.setFillingMachine_status((boolean) obj[1] ? 1 : 0);
        rawDataBean.setSterilizationMachine_status((boolean) obj[2] ? 1 : 0);
        rawDataBean.setLabelingMachine_status((boolean) obj[3] ? 1 : 0);

        // 根据判断的链道电机的状况，确定链道的逻辑起停状态
        this.setLianDaoStatus();

        sql = "select s2 from speed where id=(select max(id) from speed)";
        Double speedOfFillingMachine = (Double) statusViewDao.getBySql(sql, null).get(0);
        rawDataBean.setFillingMachine_status((speedOfFillingMachine <= 0) ? 0 : 1);// 转速为0，视为酒机停机

        rawDataBean.setBottleStopper(rawDataBean.getFillingMachine_point().get("止瓶器开"));
        /* ----------------- 读取启停状态 ---end------------------ */

        /* ----------------- 读取停机时间信息 ---begin-------------- */
        sql = "select begin_time, color from mechine_stop_information where id=( select max(id) from mechine_stop_information where machine_name= :machineName)";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("machineName", "洗瓶机");
        rawDataBean.setBottleWasher_stopTime(statusViewDao.getMachineStopTime(sql, propertyMap));
        propertyMap.put("machineName", "洗瓶机至灌装机链道");
        rawDataBean.setLianDao1_stopTime(statusViewDao.getMachineStopTime(sql, propertyMap));
        propertyMap.put("machineName", "灌装机");
        rawDataBean.setFillingMachine_stopTime(statusViewDao.getMachineStopTime(sql, propertyMap));
        propertyMap.put("machineName", "灌装机至杀菌机链道");
        rawDataBean.setLianDao2_stopTime(statusViewDao.getMachineStopTime(sql, propertyMap));
        propertyMap.put("machineName", "杀菌机");
        rawDataBean.setSterilizationMachine_stopTime(statusViewDao.getMachineStopTime(sql, propertyMap));
        propertyMap.put("machineName", "杀菌机至贴标机链道");
        rawDataBean.setLianDao3_stopTime(statusViewDao.getMachineStopTime(sql, propertyMap));
        propertyMap.put("machineName", "贴标机");
        rawDataBean.setLabelingMachine_stopTime(statusViewDao.getMachineStopTime(sql, propertyMap));
        /* ----------------- 读取停机时间信息 ---end---------------- */

        /* ----------------- 读取瓶产量等信息 ---begin-------------- */
        // TODO 获取瓶产量等信息
        /* ----------------- 读取瓶产量等信息 ---end---------------- */
    }

    /**
     * 根据判断的链道电机的状况，确定链道的逻辑起停状态
     */
    private void setLianDaoStatus() {
        Map<String, Integer> points = rawDataBean.getLianDao1_point();
        int[] motors = new int[6];
        motors[0] = points.get("M2_16");
        motors[1] = points.get("M2_17");
        motors[2] = points.get("M2_18");
        motors[3] = points.get("M3_19");
        motors[4] = points.get("M3_20");
        motors[5] = points.get("M3_21");
        boolean b = false;
        for (int i : motors) {
            if (i == 1) {
                b = true;// 存在正常电机，链道没全堵
            }
        }

        rawDataBean.setLianDao1_status(b ? 1 : 0);

        points = rawDataBean.getLianDao2_point();
        int M2_32 = points.get("M2_32");
        int M2_33 = points.get("M2_33");
        if (M2_32 == 0 && M2_33 == 0) {
            rawDataBean.setLianDao2_status(0);
        } else {
            rawDataBean.setLianDao2_status(1);
        }

        points = rawDataBean.getLianDao3_point();
        motors[0] = points.get("M4_40.4");
        motors[1] = points.get("M4_44.6");
        motors[2] = points.get("M4_47");
        motors[3] = points.get("M4_48");
        motors[4] = points.get("M5_1");
        motors[5] = points.get("M5_7");
        b = false;
        for (int i : motors) {
            if (i == 1) {
                b = true;// 存在运行正常电机，链道没停
            }
        }

        rawDataBean.setLianDao3_status(b ? 1 : 0);

        Integer m26 = rawDataBean.getLianDao4_point().get("M26");
        if (m26 == 1) {
            b = true;
        } else {
            b = false;
        }

        rawDataBean.setLianDao4_status(b ? 1 : 0);
    }

    /**
     * 判断机台状态
     */
    private void determineMachineStatus() {
        resultDataBean = machineStopAnalyse.mechineStopAnalyse(this.getResultDataBean(), this.getOldResultDataBean(),
                this.getRawDataBean(), this.getOldRawDataBean());
    }

    private PerformanceCurveService performanceCurveService;

    /**
     * 每天把绩效指标计算后存入数据库
     */
    private void ComputingPerformanceIndicators(Date date) {
        Date startTime = BigTimeUtils.getStartTimeOfDay(date);
        Date stopTime = BigTimeUtils.getEndTimeOfDay(date);
        // TODO 计算指标，保存至数据库
        String message = performanceCurveService.savePerformanceIndicatorsOfCurrentDay(startTime, stopTime);
        if (message != null) {
            Logger logger = Logger.getLogger(StatusViewServiceImpl.class);
            logger.error(message);
        }
    }

    /* -----------------------------------------------------------------------------
     * ------------------------------------
     * -----------------------------------------------------------------------------
     * ------------------------------------
     * -----------------------------------------------------------------------------
     * --------------------------------- */

    /**
     * 加载机台故障点
     */
    public List<String> init(String path) {
        FileReader fr = null;
        BufferedReader br = null;
        List<String> obj = new ArrayList<>();
        try {
            fr = new FileReader(new File(path));
            br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null) {
                if ("".equals(line)) {
                    continue;
                } else {
                    obj.add(line.split(";")[0]);
                }
            }
        } catch (IOException e) {
            System.out.println("机台故障点的txt有错！");
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    private ResultDataBean getResultDataBean() {
        if (resultDataBean == null) {
            resultDataBean = new ResultDataBean();
        }
        return resultDataBean;
    }

    private ResultDataBean getOldResultDataBean() {
        if (oldResultDataBean == null) {
            oldResultDataBean = resultDataBean.getCloneObject();
        }
        return oldResultDataBean;
    }

    private RawDataBean getRawDataBean() {
        if (rawDataBean == null) {
            rawDataBean = new RawDataBean();
            initStopTime();
        }
        return rawDataBean;
    }

    private RawDataBean getOldRawDataBean() {
        if (oldRawDataBean == null) {
            oldRawDataBean = rawDataBean.getCloneObject();
        }
        return oldRawDataBean;
    }

    public void setMachineStopAnalyse(MachineStopAnalyse machineStopAnalyse) {
        this.machineStopAnalyse = machineStopAnalyse;
    }

    public void setStatusViewDao(IStatusViewDao statusViewDao) {
        this.statusViewDao = statusViewDao;
    }

    public TimeBean getTimeBean() {
        if (timeBean == null) {
            timeBean = new TimeBean();
        }
        return timeBean;
    }

    /**
     * 给一个初始的停机时间
     */
    private void initStopTime() {
        Date future = new Date();
        future.setYear(future.getYear() + 100);
        rawDataBean.setBottleWasher_stopTime(future);
        rawDataBean.setFillingMachine_stopTime(future);
        rawDataBean.setSterilizationMachine_stopTime(future);
        rawDataBean.setLabelingMachine_stopTime(future);
        rawDataBean.setLianDao1_stopTime(future);
        rawDataBean.setLianDao2_stopTime(future);
        rawDataBean.setLianDao3_stopTime(future);
    }

    public TimeChooseParameterBean getParams() {
        return params;
    }

    public void setParams(TimeChooseParameterBean params) {
        this.params = params;
    }

    public void setPerformanceCurveService(PerformanceCurveService performanceCurveService) {
        this.performanceCurveService = performanceCurveService;
    }
}