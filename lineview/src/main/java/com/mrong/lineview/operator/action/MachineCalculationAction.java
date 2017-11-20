package com.mrong.lineview.operator.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mrong.lineview.operator.dao.ModeChangeDao;
import com.mrong.lineview.operator.entity.ModeRecord;
import com.mrong.lineview.util.TimeUtils;

@SuppressWarnings("all")
public class MachineCalculationAction {
    // 前台传来的开始时间
    private String startTime;
    // 前台传来的大小停机总时间
    private int countStopTime;
    // 获取前台机台名称
    private int machineName;
    // 设备利用率
    private int equipmentUR;
    // 绩效
    private int performance;

    public int getEquipmentUR() {
        return equipmentUR;
    }

    public void setEquipmentUR(int equipmentUR) {
        this.equipmentUR = equipmentUR;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public int getCountStopTime() {
        return countStopTime;
    }

    public void setCountStopTime(int countStopTime) {
        this.countStopTime = countStopTime;
    }

    public int getMachineName() {
        return machineName;
    }

    public void setMachineName(int machineName) {
        this.machineName = machineName;
    }

    private ModeChangeDao modeChangeDao;

    public void setModeChangeDao(ModeChangeDao modeChangeDao) {
        this.modeChangeDao = modeChangeDao;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    // 获取机器从换线开始到现在的工作时间
    public int workCount() {
        ModeRecord modeRecord = new ModeRecord();
        modeRecord = modeChangeDao.getTime();
        long workCountTime = TimeUtils.calculate(new Date(), modeRecord.getStartTime());
        return (int) workCountTime;
    }

    // 计算从上班到现在的总时间
    public int countTime() {
        int count = 0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String pinjie = df.format(new Date()) + " " + startTime;
        Date date1 = null;
        try {
            date1 = df1.parse(pinjie);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long shijian = TimeUtils.calculate(new Date(), date1);
        count = (int) shijian;
        return count;
    }

    public int BottleWashingMachineEfficiency() {
        // 获取洗瓶机从工作到现在的工作总时间

        int tttime = workCount();
        // 获得每班从工作到现在的应该工作的时间
        int stopTime = countTime();
        // 计算获得洗瓶机的利用率(返回int数组)
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 洗瓶机绩效
     */
    public int BottleWashingMachineAchievements() {
        // 获取洗瓶机从上班到现在的工作总时间
        int tttime = countTime();
        // 获得洗瓶机到现在工作的中大小停机的总时间
        int stopTime = countStopTime;
        // 得到洗瓶机的绩效百分比(返回int数据)
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 洗瓶机质量 质量：1-剔除损失时间/（查询时间-无生产需求时间-计划停机时间-停机时间-速度损失时间）
     */
    public int BottleWashingMachineQuality() {
        // 获得洗瓶机的剔除损失时间(有问题啊)
        int sunshiTime = 20;
        // 获得查询时间(1.从工作到现在)
        int chaxunTime = 2000;
        // 无生产需求时间(工作开始到现在)
        int noPlayStopTime = 45;
        // 洗瓶机的计划停机时间
        int PlayStopTime = 55;
        // 洗瓶机停机时间
        int StopTime = 55;
        // 洗瓶机速度损失时间
        int VelocityLossTime = 100;
        // 得到洗瓶机的质量百分比(返回int数据)
        double result = 1
                - ((double) sunshiTime / (chaxunTime - noPlayStopTime - PlayStopTime - StopTime - VelocityLossTime));
        System.out.println(result);
        return (int) (result * 100);
    }

    /**
     * 酒机利用率
     */
    public int WineMachineEfficiency() {
        // 获取酒机从工作到现在的工作总时间
        int tttime = workCount();
        // 获得每班从工作到现在的应该工作的时间
        int stopTime = countTime();
        // 获取酒机从工作到现在的利用率
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 酒机绩效
     */
    public int WineMachineAchievements() {
        // 获取酒机从工作到现在的工作总时间
        int tttime = countTime();
        // 获得酒机到现在工作的中大小停机的总时间
        int stopTime = countStopTime;
        // 得到酒机的绩效比率(返回的是 int 百分比)
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 酒机质量 质量：1-剔除损失时间/（查询时间-无生产需求时间-计划停机时间-停机时间-速度损失时间）
     */
    public int WineMachineQuality() {
        // 获得酒机的剔除损失时间
        int sunshiTime = 20;
        // 获得酒机查询时间(开始上班到现在)
        int chaxunTime = 2000;
        // 获取在工作期间无生产需求时间
        int noPlayStopTime = 45;
        // 获取在工作期间计划停机时间
        int PlayStopTime = 55;
        // 获取在工作期间停机大小停机的总时间
        int StopTime = 55;
        // 获取在工作期间速度损失时间
        int VelocityLossTime = 100;
        // 计算
        double result = 1
                - ((double) sunshiTime / (chaxunTime - noPlayStopTime - PlayStopTime - StopTime - VelocityLossTime));
        System.out.println(result);
        return (int) (result * 100);
    }

    /**
     * 杀菌机利用率
     */
    public int SterilizationMachineEfficiency() {
        // 获得杀菌机的设备的工作总时间
        int tttime = workCount();
        // 获得每班从工作到现在的应该工作的时间
        int stopTime = countTime();
        // 计算获得利用率的百分比(返回int型)
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 杀菌机绩效
     */
    public int SterilizationMachineAchievements() {
        // 获得杀菌机的设备的工作总时间
        int tttime = countTime();
        // 获得杀菌机到现在工作的中大小停机的总时间
        int stopTime = countStopTime;
        // 计算获得利用率的百分比(返回int型)
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 杀菌机质量 质量：1-剔除损失时间/（查询时间-无生产需求时间-计划停机时间-停机时间-速度损失时间）
     */
    public int SterilizationMachineQuality() {
        // 获得杀菌机的剔除损失时间
        int sunshiTime = 20;
        // 获得杀菌机的查询时间
        int chaxunTime = 2000;
        // 获得杀菌机的无生产需求时间
        int noPlayStopTime = 45;

        // 获得杀菌机的计划停机时间
        int PlayStopTime = 55;
        // 获得杀菌机的停机时间
        int StopTime = 55;
        // 获得杀菌机的速度损失时间
        int VelocityLossTime = 100;
        // 计算杀菌机的质量(1-剔除损失时间/（查询时间-无生产需求时间-计划停机时间-停机时间-速度损失时间）)
        double result = 1
                - ((double) sunshiTime / (chaxunTime - noPlayStopTime - PlayStopTime - StopTime - VelocityLossTime));
        System.out.println(result);
        return (int) (result * 100);
    }

    /**
     * 贴标机利用率
     */
    public int LabelingMachineEfficiency() {
        // 获得贴标机的设备的工作总时间
        int tttime = workCount();
        // 获得每班从工作到现在的应该工作的时间
        int stopTime = countTime();
        // 计算获得利用率的百分比(返回int型)
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 贴标机绩效
     */
    public int LabelingMachineAchievements() {
        // 获得贴标机的设备的工作总时间
        int tttime = countTime();
        // 获得贴标机到现在工作的中大小停机的总时间
        int stopTime = countStopTime;
        // 计算获得利用率的百分比(返回int型)
        int b = (int) (((double) (tttime - stopTime) / tttime) * 100);
        return b;
    }

    /**
     * 贴标机质量 质量：1-剔除损失时间/（查询时间-无生产需求时间-计划停机时间-停机时间-速度损失时间）
     */
    public int LabelingMachineQuality() {
        // 获得贴标机剔除损失时间
        int sunshiTime = 20;
        // 获得贴标机查询时间
        int chaxunTime = 2000;
        // 贴标机无生产需求时间
        int noPlayStopTime = 45;

        // 贴标机计划停机时间
        int PlayStopTime = 55;
        // 贴标机停机时间
        int StopTime = 55;
        // 贴标机速度损失时间
        int VelocityLossTime = 100;
        // 计算贴标机质量(1-剔除损失时间/(查询时间-无生产需求时间-计划停机时间-停机时间-速度损失时间))
        double result = 1
                - ((double) sunshiTime / (chaxunTime - noPlayStopTime - PlayStopTime - StopTime - VelocityLossTime));
        System.out.println(result);
        return (int) (result * 100);
    }

    // 获取前台名称返回对应的计算值
    public String jisuan() {
        boolean flag = false;
        if (machineName == 1) {
            // 返回洗瓶机
            equipmentUR = BottleWashingMachineEfficiency();
            performance = BottleWashingMachineAchievements();
            flag = true;

        }
        if (machineName == 2) {
            // 返回灌酒机
            equipmentUR = WineMachineEfficiency();
            performance = WineMachineAchievements();
            flag = true;
        }
        if (machineName == 3) {
            // 返回杀菌机
            equipmentUR = SterilizationMachineEfficiency();
            performance = SterilizationMachineAchievements();
            flag = true;
        }
        if (machineName == 4) {
            // 返回贴标机
            equipmentUR = LabelingMachineEfficiency();
            performance = LabelingMachineAchievements();
            flag = true;
        }
        if (flag) {

        } else {
            System.out.println("有错误！");
        }
        return "success";
    }

}
