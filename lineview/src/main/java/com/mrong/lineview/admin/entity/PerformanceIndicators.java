package com.mrong.lineview.admin.entity;

/**
 * 绩效指标实体类
 * 
 * @author 林金成
 *         2017年11月17日
 */
public class PerformanceIndicators {

    private double oee;
    private double lef;
    private double gly;
    private double yield;

    public double getOee() {
        return oee;
    }

    public PerformanceIndicators() {
        super();
    }

    public PerformanceIndicators(double oee, double lef, double gly, double yield) {
        super();
        this.oee = oee;
        this.lef = lef;
        this.gly = gly;
        this.yield = yield;
    }

    public void setOee(double oee) {
        this.oee = oee;
    }

    public double getLef() {
        return lef;
    }

    public void setLef(double lef) {
        this.lef = lef;
    }

    public double getGly() {
        return gly;
    }

    public void setGly(double gly) {
        this.gly = gly;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }
}
