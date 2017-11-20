package com.mrong.lineview.operator.entity;

public class OnlineCheck {
    private Integer id;
    // 0代表否1代表是
    // 是否佩戴pee
    private String pee;
    // 质量工具是否到位
    private String qualityTools;
    // 地面机台是否干净
    private String groundMachine;
    /* 检查工作 */
    // 抽氢风机
    private String fan1;
    // 洗瓶机进出口光电开关急停是否有效
    private String photoelectricSwitch;
    // 洗瓶机最后一道喷淋水品评
    private String sprayPump;
    // 1#预喷淋压力
    private String preSpray1;
    // 2#预喷淋压力
    private String preSpray2;
    // 1#碱槽预喷淋压力
    private String alkalipreSpray1;
    // 2#碱槽预喷淋压力
    private String alkalipreSpray2;
    // 1#碱槽温度
    private String alkaliT1;
    // 2#碱槽温度
    private String alkaliT2;
    // 洗瓶机喷淋软化水
    private String bottleWasherWater;
    // 喷淋酿造水
    private String sprayWater;
    // 杀菌机软化水
    private String sterilizerWater;
    // 杀菌机蒸汽压力
    private String sterilizerPressure;
    // 杀菌总空压
    private String sterilizerAirPressure;
    private String operator;

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getFan1() {
        return fan1;
    }

    public void setFan1(String fan1) {
        this.fan1 = fan1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPee() {
        return pee;
    }

    public void setPee(String pee) {
        this.pee = pee;
    }

    public String getQualityTools() {
        return qualityTools;
    }

    public void setQualityTools(String qualityTools) {
        this.qualityTools = qualityTools;
    }

    public String getGroundMachine() {
        return groundMachine;
    }

    public void setGroundMachine(String groundMachine) {
        this.groundMachine = groundMachine;
    }

    public String getPhotoelectricSwitch() {
        return photoelectricSwitch;
    }

    public void setPhotoelectricSwitch(String photoelectricSwitch) {
        this.photoelectricSwitch = photoelectricSwitch;
    }

    public String getSprayPump() {
        return sprayPump;
    }

    public void setSprayPump(String sprayPump) {
        this.sprayPump = sprayPump;
    }

    public String getPreSpray1() {
        return preSpray1;
    }

    public void setPreSpray1(String preSpray1) {
        this.preSpray1 = preSpray1;
    }

    public String getPreSpray2() {
        return preSpray2;
    }

    public void setPreSpray2(String preSpray2) {
        this.preSpray2 = preSpray2;
    }

    public String getAlkalipreSpray1() {
        return alkalipreSpray1;
    }

    public void setAlkalipreSpray1(String alkalipreSpray1) {
        this.alkalipreSpray1 = alkalipreSpray1;
    }

    public String getAlkalipreSpray2() {
        return alkalipreSpray2;
    }

    public void setAlkalipreSpray2(String alkalipreSpray2) {
        this.alkalipreSpray2 = alkalipreSpray2;
    }

    public String getAlkaliT1() {
        return alkaliT1;
    }

    public void setAlkaliT1(String alkaliT1) {
        this.alkaliT1 = alkaliT1;
    }

    public String getAlkaliT2() {
        return alkaliT2;
    }

    public void setAlkaliT2(String alkaliT2) {
        this.alkaliT2 = alkaliT2;
    }

    public String getBottleWasherWater() {
        return bottleWasherWater;
    }

    public void setBottleWasherWater(String bottleWasherWater) {
        this.bottleWasherWater = bottleWasherWater;
    }

    public String getSprayWater() {
        return sprayWater;
    }

    public void setSprayWater(String sprayWater) {
        this.sprayWater = sprayWater;
    }

    public String getSterilizerWater() {
        return sterilizerWater;
    }

    public void setSterilizerWater(String sterilizerWater) {
        this.sterilizerWater = sterilizerWater;
    }

    public String getSterilizerPressure() {
        return sterilizerPressure;
    }

    public void setSterilizerPressure(String sterilizerPressure) {
        this.sterilizerPressure = sterilizerPressure;
    }

    public String getSterilizerAirPressure() {
        return sterilizerAirPressure;
    }

    public void setSterilizerAirPressure(String sterilizerAirPressure) {
        this.sterilizerAirPressure = sterilizerAirPressure;
    }

    @Override
    public String toString() {
        return " " + id + ", " + pee + ", " + qualityTools + ", " + groundMachine + ", " + fan1 + ", "
                + photoelectricSwitch + ", " + sprayPump + ", " + preSpray1 + ", " + preSpray2 + ", " + alkalipreSpray1
                + ", " + alkalipreSpray2 + ", " + alkaliT1 + ", " + alkaliT2 + ", " + bottleWasherWater + ", "
                + sprayWater + ", " + sterilizerWater + ", " + sterilizerPressure + ", " + sterilizerAirPressure;
    }

}
