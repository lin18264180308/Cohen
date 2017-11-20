package com.mrong.lineview.common.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mrong.lineview.operator.entity.MechineStopInformationBean;

/**
 * 原始数据实体类
 * 
 * @author 林金成
 *         2017年10月11日
 */
@Component("rawDataBean")
public class RawDataBean implements Cloneable {

    private int yield;//瓶产量
    private int reject;//瓶剔除
    private int bottleStopper;// 止瓶器信号

    private Integer bottleWasher_status;//洗瓶机状态
    private Map<String, Integer> bottleWasher_point;// 洗瓶机故障点
    private Date bottleWasher_stopTime;// 洗瓶机停机时间

    private Integer fillingMachine_status;// 灌装机状态
    private Map<String, Integer> fillingMachine_point;// 灌装机故障点
    private Date fillingMachine_stopTime;// 洗瓶机停机时间

    private Integer sterilizationMachine_status;// 杀菌机状态
    private Map<String, Integer> sterilizationMachine_point;// 杀菌机故障点
    private Date sterilizationMachine_stopTime;// 洗瓶机停机时间

    private Integer labelingMachine_status;// 德龙贴标机状态
    private Map<String, Integer> labelingMachine_point;// 德龙贴标机故障点
    private Date labelingMachine_stopTime;// 德龙洗瓶机停机时间

    /* private int labelingMachine_status2;// 特朗斯贴标机状态
     * private Map<String, Integer> labelingMachine_point2;// 特朗斯贴标机故障点
     * private Date labelingMachine_stopTime2;// 特朗斯洗瓶机停机时间 */

    private Integer lianDao1_status;//洗瓶机至灌装机链道
    private Map<String, Integer> lianDao1_point;// 洗瓶机至灌装机链道故障点
    private Date lianDao1_stopTime;//洗瓶机至灌装机链道停机时间

    private Integer lianDao4_status;// EBI至灌装机链道
    private Map<String, Integer> lianDao4_point;
    private Date lianDao4_stopTime;

    private Integer lianDao2_status;//灌装机至杀菌机链道
    private Map<String, Integer> lianDao2_point;// 灌装机至杀菌机链道故障点
    private Date lianDao2_stopTime;//灌装机至杀菌机链道停机时间

    private Integer lianDao3_status;//杀菌机至贴标机链道
    private Map<String, Integer> lianDao3_point;// 杀菌机至贴标机链道故障点
    private Date lianDao3_stopTime;//杀菌机至贴标机链道停机时间

    private List<MechineStopInformationBean> stopInfomationOfBottleWasher;
    private List<MechineStopInformationBean> stopInfomationOfFillingMachine;
    private List<MechineStopInformationBean> stopInfomationOfSterilizationMachine;
    private List<MechineStopInformationBean> stopInfomationOfLabelingMachine;

    /**
     * 电机人为控制信号
     */
    private Map<String, Integer> manualSignal;

    public Map<String, Integer> getManualSignal() {
        return manualSignal;
    }

    public void setManualSignal(Map<String, Integer> manualSignal) {
        this.manualSignal = manualSignal;
    }

    private int labelingMachingFlg = 1;// 贴标机选择方案，为1时选择德龙贴标机，为2时选择特朗斯贴标机，为3时选择两台贴标机同时运转

    public List<MechineStopInformationBean> getStopInfomationOfBottleWasher() {
        return stopInfomationOfBottleWasher;
    }

    public void setStopInfomationOfBottleWasher(List<MechineStopInformationBean> stopInfomationOfBottleWasher) {
        this.stopInfomationOfBottleWasher = stopInfomationOfBottleWasher;
    }

    public List<MechineStopInformationBean> getStopInfomationOfFillingMachine() {
        return stopInfomationOfFillingMachine;
    }

    public void setStopInfomationOfFillingMachine(List<MechineStopInformationBean> stopInfomationOfFillingMachine) {
        this.stopInfomationOfFillingMachine = stopInfomationOfFillingMachine;
    }

    public List<MechineStopInformationBean> getStopInfomationOfSterilizationMachine() {
        return stopInfomationOfSterilizationMachine;
    }

    public void setStopInfomationOfSterilizationMachine(
            List<MechineStopInformationBean> stopInfomationOfSterilizationMachine) {
        this.stopInfomationOfSterilizationMachine = stopInfomationOfSterilizationMachine;
    }

    public List<MechineStopInformationBean> getStopInfomationOfLabelingMachine() {
        return stopInfomationOfLabelingMachine;
    }

    public void setStopInfomationOfLabelingMachine(List<MechineStopInformationBean> stopInfomationOfLabelingMachine) {
        this.stopInfomationOfLabelingMachine = stopInfomationOfLabelingMachine;
    }

    public int getLabelingMachingFlg() {
        return labelingMachingFlg;
    }

    public void setLabelingMachingFlg(int labelingMachingFlg) {
        this.labelingMachingFlg = labelingMachingFlg;
    }

    public int getYield() {
        return yield;
    }

    public void setYield(int yield) {
        this.yield = yield;
    }

    public int getReject() {
        return reject;
    }

    public void setReject(int reject) {
        this.reject = reject;
    }

    public Integer getBottleWasher_status() {
        return bottleWasher_status;
    }

    public void setBottleWasher_status(int bottleWasher_status) {
        this.bottleWasher_status = bottleWasher_status;
    }

    public Map<String, Integer> getBottleWasher_point() {
        return bottleWasher_point;
    }

    public void setBottleWasher_point(Map<String, Integer> bottleWasher_point) {
        this.bottleWasher_point = bottleWasher_point;
    }

    public Date getBottleWasher_stopTime() {
        return bottleWasher_stopTime;
    }

    public void setBottleWasher_stopTime(Date bottleWasher_stopTime) {
        this.bottleWasher_stopTime = bottleWasher_stopTime;
    }

    public int getBottleStopper() {
        return bottleStopper;
    }

    public void setBottleStopper(int bottleStopper) {
        this.bottleStopper = bottleStopper;
    }

    public Integer getFillingMachine_status() {
        return fillingMachine_status;
    }

    public void setFillingMachine_status(int fillingMachine_status) {
        this.fillingMachine_status = fillingMachine_status;
    }

    public Map<String, Integer> getFillingMachine_point() {
        return fillingMachine_point;
    }

    public void setFillingMachine_point(Map<String, Integer> fillingMachine_point) {
        this.fillingMachine_point = fillingMachine_point;
    }

    public Date getFillingMachine_stopTime() {
        return fillingMachine_stopTime;
    }

    public void setFillingMachine_stopTime(Date fillingMachine_stopTime) {
        this.fillingMachine_stopTime = fillingMachine_stopTime;
    }

    public Integer getSterilizationMachine_status() {
        return sterilizationMachine_status;
    }

    public void setSterilizationMachine_status(int sterilizationMachine_status) {
        this.sterilizationMachine_status = sterilizationMachine_status;
    }

    public Map<String, Integer> getSterilizationMachine_point() {
        return sterilizationMachine_point;
    }

    public void setSterilizationMachine_point(Map<String, Integer> sterilizationMachine_point) {
        this.sterilizationMachine_point = sterilizationMachine_point;
    }

    public Date getSterilizationMachine_stopTime() {
        return sterilizationMachine_stopTime;
    }

    public void setSterilizationMachine_stopTime(Date sterilizationMachine_stopTime) {
        this.sterilizationMachine_stopTime = sterilizationMachine_stopTime;
    }

    public Integer getLabelingMachine_status() {
        return labelingMachine_status;
    }

    public void setLabelingMachine_status(int labelingMachine_status) {
        this.labelingMachine_status = labelingMachine_status;
    }

    public Map<String, Integer> getLabelingMachine_point() {
        return labelingMachine_point;
    }

    public void setLabelingMachine_point(Map<String, Integer> labelingMachine_point) {
        this.labelingMachine_point = labelingMachine_point;
    }

    public Date getLabelingMachine_stopTime() {
        return labelingMachine_stopTime;
    }

    public void setLabelingMachine_stopTime(Date labelingMachine_stopTime) {
        this.labelingMachine_stopTime = labelingMachine_stopTime;
    }

    public Integer getLianDao1_status() {
        return lianDao1_status;
    }

    public void setLianDao1_status(int lianDao1_status) {
        this.lianDao1_status = lianDao1_status;
    }

    public Map<String, Integer> getLianDao1_point() {
        return lianDao1_point;
    }

    public void setLianDao1_point(Map<String, Integer> lianDao1_point) {
        this.lianDao1_point = lianDao1_point;
    }

    public Integer getLianDao2_status() {
        return lianDao2_status;
    }

    public void setLianDao2_status(int lianDao2_status) {
        this.lianDao2_status = lianDao2_status;
    }

    public Map<String, Integer> getLianDao2_point() {
        return lianDao2_point;
    }

    public void setLianDao2_point(Map<String, Integer> lianDao2_point) {
        this.lianDao2_point = lianDao2_point;
    }

    public Integer getLianDao3_status() {
        return lianDao3_status;
    }

    public void setLianDao3_status(int lianDao3_status) {
        this.lianDao3_status = lianDao3_status;
    }

    public Map<String, Integer> getLianDao3_point() {
        return lianDao3_point;
    }

    public void setLianDao3_point(Map<String, Integer> lianDao3_point) {
        this.lianDao3_point = lianDao3_point;
    }

    public Date getLianDao1_stopTime() {
        return lianDao1_stopTime;
    }

    public void setLianDao1_stopTime(Date lianDao1_stopTime) {
        this.lianDao1_stopTime = lianDao1_stopTime;
    }

    public Date getLianDao2_stopTime() {
        return lianDao2_stopTime;
    }

    public void setLianDao2_stopTime(Date lianDao2_stopTime) {
        this.lianDao2_stopTime = lianDao2_stopTime;
    }

    public Date getLianDao3_stopTime() {
        return lianDao3_stopTime;
    }

    public void setLianDao3_stopTime(Date lianDao3_stopTime) {
        this.lianDao3_stopTime = lianDao3_stopTime;
    }

    public Integer getLianDao4_status() {
        return lianDao4_status;
    }

    public void setLianDao4_status(Integer lianDao4_status) {
        this.lianDao4_status = lianDao4_status;
    }

    public Map<String, Integer> getLianDao4_point() {
        return lianDao4_point;
    }

    public void setLianDao4_point(Map<String, Integer> lianDao4_point) {
        this.lianDao4_point = lianDao4_point;
    }

    public Date getLianDao4_stopTime() {
        return lianDao4_stopTime;
    }

    public void setLianDao4_stopTime(Date lianDao4_stopTime) {
        this.lianDao4_stopTime = lianDao4_stopTime;
    }

    public void setBottleWasher_status(Integer bottleWasher_status) {
        this.bottleWasher_status = bottleWasher_status;
    }

    public void setFillingMachine_status(Integer fillingMachine_status) {
        this.fillingMachine_status = fillingMachine_status;
    }

    public void setSterilizationMachine_status(Integer sterilizationMachine_status) {
        this.sterilizationMachine_status = sterilizationMachine_status;
    }

    public void setLabelingMachine_status(Integer labelingMachine_status) {
        this.labelingMachine_status = labelingMachine_status;
    }

    public void setLianDao1_status(Integer lianDao1_status) {
        this.lianDao1_status = lianDao1_status;
    }

    public void setLianDao2_status(Integer lianDao2_status) {
        this.lianDao2_status = lianDao2_status;
    }

    public void setLianDao3_status(Integer lianDao3_status) {
        this.lianDao3_status = lianDao3_status;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public RawDataBean getCloneObject() {
        RawDataBean obj = null;
        try {
            obj = (RawDataBean) this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("ResultDataBean 对象的 clone 方法出现问题！");
        }
        return obj;
    }
}