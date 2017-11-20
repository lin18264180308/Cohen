package com.mrong.lineview.common.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mrong.lineview.operator.entity.TimesOfMachineStop;

/**
 * 处理后的结果数据
 * 
 * @author 林金成
 */
@Component("resultDataBean")
public class ResultDataBean implements Cloneable {

    private double oee;// 设备利用率
    private double gyl;// 线产出率
    private double lef;// 线效率
    private Integer yield;// 瓶产量
    private Integer eliminate;// 瓶剔除
    private Integer lose;// 瓶损失
    private Date loseTime;// 损失时间

    @Override
    public String toString() {
        return "ResultDataBean [oee=" + oee + ", gyl=" + gyl + ", lef=" + lef + ", yield=" + yield + ", eliminate="
                + eliminate + ", lose=" + lose + ", loseTime=" + loseTime + ", bottleWasher_color=" + bottleWasher_color
                + ", bottleWasher_reason=" + bottleWasher_reason + ", fillingMachine_color=" + fillingMachine_color
                + ", fillingMachine_reason=" + fillingMachine_reason + ", sterilizationMachine_color="
                + sterilizationMachine_color + ", sterilizationMachine_reason=" + sterilizationMachine_reason
                + ", labelingMachine_color=" + labelingMachine_color + ", labelingMachine_reason="
                + labelingMachine_reason + ", lianDao1_color=" + lianDao1_color + ", lianDao1_reason=" + lianDao1_reason
                + ", lianDao2_color=" + lianDao2_color + ", lianDao2_reason=" + lianDao2_reason + ", lianDao3_color="
                + lianDao3_color + ", lianDao3_reason=" + lianDao3_reason + "]";
    }

    private String bottleWasher_color;// 洗瓶机颜色
    private List<String> bottleWasher_reason;// 洗瓶机原因
    private TimesOfMachineStop stopInfomationOfBottleWasher;// 洗瓶机大停机小停机信息
    private Map<String, Integer> faultTimesOfBottleWasher;// 洗瓶机故障点以及出现的次数
    private int aveSpeedOfBottleWasher;// 洗瓶机平均速度

    private String fillingMachine_color;// 灌装机颜色
    private List<String> fillingMachine_reason;// 灌装机原因
    private TimesOfMachineStop stopInfomationOfFillingMachine;// 灌装机大停机小停机信息
    private Map<String, Integer> faultTimesOfFillingMachine;// 灌装机故障点以及出现的次数
    private int aveSpeedOfFillingMachine;// 洗瓶机平均速度

    private String sterilizationMachine_color;// 杀菌机颜色
    private List<String> sterilizationMachine_reason;// 杀菌机原因
    private TimesOfMachineStop stopInfomationOfSterilizationMachine;// 杀菌机机大停机小停机信息
    private Map<String, Integer> faultTimesOfSterilizationMachine;// 杀菌机故障点以及出现的次数
    private int aveSpeedOfSterilizationMachine;// 洗瓶机平均速度

    private String labelingMachine_color;//贴标机颜色
    private List<String> labelingMachine_reason;//贴标机原因
    private TimesOfMachineStop stopInfomationOfLabelingMachine;// 贴标机大停机小停机信息
    private Map<String, Integer> faultTimesOfLabelingMachine;// 贴标机机故障点以及出现的次数
    private int aveSpeedOfLabelingMachine;// 洗瓶机平均速度

    private String lianDao1_color;// 洗瓶机至灌装机链道
    private List<String> lianDao1_reason;// 洗瓶机至灌装机链道

    private String lianDao4_color;// EBI至灌装机链道
    private List<String> lianDao4_reason;// EBI至灌装机链道

    private String lianDao2_color;// 灌装机至杀菌机链道
    private List<String> lianDao2_reason;// 灌装机至杀菌机链道

    private String lianDao3_color;// 杀菌机至贴标机链道
    private List<String> lianDao3_reason;// 杀菌机至贴标机链道

    public TimesOfMachineStop getStopInfomationOfBottleWasher() {
        return stopInfomationOfBottleWasher;
    }

    public void setStopInfomationOfBottleWasher(TimesOfMachineStop stopInfomationOfBottleWasher) {
        this.stopInfomationOfBottleWasher = stopInfomationOfBottleWasher;
    }

    public TimesOfMachineStop getStopInfomationOfFillingMachine() {
        return stopInfomationOfFillingMachine;
    }

    public void setStopInfomationOfFillingMachine(TimesOfMachineStop stopInfomationOfFillingMachine) {
        this.stopInfomationOfFillingMachine = stopInfomationOfFillingMachine;
    }

    public TimesOfMachineStop getStopInfomationOfSterilizationMachine() {
        return stopInfomationOfSterilizationMachine;
    }

    public void setStopInfomationOfSterilizationMachine(TimesOfMachineStop stopInfomationOfSterilizationMachine) {
        this.stopInfomationOfSterilizationMachine = stopInfomationOfSterilizationMachine;
    }

    public TimesOfMachineStop getStopInfomationOfLabelingMachine() {
        return stopInfomationOfLabelingMachine;
    }

    public void setStopInfomationOfLabelingMachine(TimesOfMachineStop stopInfomationOfLabelingMachine) {
        this.stopInfomationOfLabelingMachine = stopInfomationOfLabelingMachine;
    }

    public double getOee() {
        return oee;
    }

    public void setOee(double oee) {
        this.oee = oee;
    }

    public double getGyl() {
        return gyl;
    }

    public void setGyl(double gyl) {
        this.gyl = gyl;
    }

    public double getLef() {
        return lef;
    }

    public Integer getYield() {
        return yield;
    }

    public void setYield(Integer yield) {
        this.yield = yield;
    }

    public Integer getEliminate() {
        return eliminate;
    }

    public void setEliminate(Integer eliminate) {
        this.eliminate = eliminate;
    }

    public void setLef(double lef) {
        this.lef = lef;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Date getLoseTime() {
        return loseTime;
    }

    public void setLoseTime(Date loseTime) {
        this.loseTime = loseTime;
    }

    public String getBottleWasher_color() {
        return bottleWasher_color;
    }

    public void setBottleWasher_color(String bottleWasher_color) {
        this.bottleWasher_color = bottleWasher_color;
    }

    public List<String> getBottleWasher_reason() {
        return bottleWasher_reason;
    }

    public void setBottleWasher_reason(List<String> bottleWasher_reason) {
        this.bottleWasher_reason = bottleWasher_reason;
    }

    public String getFillingMachine_color() {
        return fillingMachine_color;
    }

    public void setFillingMachine_color(String fillingMachine_color) {
        this.fillingMachine_color = fillingMachine_color;
    }

    public List<String> getFillingMachine_reason() {
        return fillingMachine_reason;
    }

    public void setFillingMachine_reason(List<String> fillingMachine_reason) {
        this.fillingMachine_reason = fillingMachine_reason;
    }

    public String getSterilizationMachine_color() {
        return sterilizationMachine_color;
    }

    public void setSterilizationMachine_color(String sterilizationMachine_color) {
        this.sterilizationMachine_color = sterilizationMachine_color;
    }

    public List<String> getSterilizationMachine_reason() {
        return sterilizationMachine_reason;
    }

    public void setSterilizationMachine_reason(List<String> sterilizationMachine_reason) {
        this.sterilizationMachine_reason = sterilizationMachine_reason;
    }

    public String getLabelingMachine_color() {
        return labelingMachine_color;
    }

    public void setLabelingMachine_color(String labelingMachine_color) {
        this.labelingMachine_color = labelingMachine_color;
    }

    public List<String> getLabelingMachine_reason() {
        return labelingMachine_reason;
    }

    public void setLabelingMachine_reason(List<String> labelingMachine_reason) {
        this.labelingMachine_reason = labelingMachine_reason;
    }

    public String getLianDao1_color() {
        return lianDao1_color;
    }

    public void setLianDao1_color(String lianDao1_color) {
        this.lianDao1_color = lianDao1_color;
    }

    public List<String> getLianDao1_reason() {
        return lianDao1_reason;
    }

    public void setLianDao1_reason(List<String> lianDao1_reason) {
        this.lianDao1_reason = lianDao1_reason;
    }

    public String getLianDao2_color() {
        return lianDao2_color;
    }

    public void setLianDao2_color(String lianDao2_color) {
        this.lianDao2_color = lianDao2_color;
    }

    public List<String> getLianDao2_reason() {
        return lianDao2_reason;
    }

    public void setLianDao2_reason(List<String> lianDao2_reason) {
        this.lianDao2_reason = lianDao2_reason;
    }

    public String getLianDao3_color() {
        return lianDao3_color;
    }

    public void setLianDao3_color(String lianDao3_color) {
        this.lianDao3_color = lianDao3_color;
    }

    public List<String> getLianDao3_reason() {
        return lianDao3_reason;
    }

    public void setLianDao3_reason(List<String> lianDao3_reason) {
        this.lianDao3_reason = lianDao3_reason;
    }

    public String getLianDao4_color() {
        return lianDao4_color;
    }

    public void setLianDao4_color(String lianDao4_color) {
        this.lianDao4_color = lianDao4_color;
    }

    public List<String> getLianDao4_reason() {
        return lianDao4_reason;
    }

    public void setLianDao4_reason(List<String> lianDao4_reason) {
        this.lianDao4_reason = lianDao4_reason;
    }

    public Map<String, Integer> getFaultTimesOfBottleWasher() {
        return faultTimesOfBottleWasher;
    }

    public void setFaultTimesOfBottleWasher(Map<String, Integer> faultTimesOfBottleWasher) {
        this.faultTimesOfBottleWasher = faultTimesOfBottleWasher;
    }

    public Map<String, Integer> getFaultTimesOfFillingMachine() {
        return faultTimesOfFillingMachine;
    }

    public void setFaultTimesOfFillingMachine(Map<String, Integer> faultTimesOfFillingMachine) {
        this.faultTimesOfFillingMachine = faultTimesOfFillingMachine;
    }

    public Map<String, Integer> getFaultTimesOfSterilizationMachine() {
        return faultTimesOfSterilizationMachine;
    }

    public void setFaultTimesOfSterilizationMachine(Map<String, Integer> faultTimesOfSterilizationMachine) {
        this.faultTimesOfSterilizationMachine = faultTimesOfSterilizationMachine;
    }

    public Map<String, Integer> getFaultTimesOfLabelingMachine() {
        return faultTimesOfLabelingMachine;
    }

    public void setFaultTimesOfLabelingMachine(Map<String, Integer> faultTimesOfLabelingMachine) {
        this.faultTimesOfLabelingMachine = faultTimesOfLabelingMachine;
    }

    public int getAveSpeedOfBottleWasher() {
        return aveSpeedOfBottleWasher;
    }

    public void setAveSpeedOfBottleWasher(int aveSpeedOfBottleWasher) {
        this.aveSpeedOfBottleWasher = aveSpeedOfBottleWasher;
    }

    public int getAveSpeedOfFillingMachine() {
        return aveSpeedOfFillingMachine;
    }

    public void setAveSpeedOfFillingMachine(int aveSpeedOfFillingMachine) {
        this.aveSpeedOfFillingMachine = aveSpeedOfFillingMachine;
    }

    public int getAveSpeedOfSterilizationMachine() {
        return aveSpeedOfSterilizationMachine;
    }

    public void setAveSpeedOfSterilizationMachine(int aveSpeedOfSterilizationMachine) {
        this.aveSpeedOfSterilizationMachine = aveSpeedOfSterilizationMachine;
    }

    public int getAveSpeedOfLabelingMachine() {
        return aveSpeedOfLabelingMachine;
    }

    public void setAveSpeedOfLabelingMachine(int aveSpeedOfLabelingMachine) {
        this.aveSpeedOfLabelingMachine = aveSpeedOfLabelingMachine;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public ResultDataBean getCloneObject() {
        ResultDataBean obj = null;
        try {
            obj = (ResultDataBean) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("ResultDataBean 对象的 clone 方法出现问题！");
        }
        return obj;
    }
}
