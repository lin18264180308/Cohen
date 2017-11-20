package com.mrong.lineview.common.entity;

import java.util.Date;

/**
 * 四大几台的速度信号还有灌酒机和贴标机的计数信号
 * 
 * @author 张裕宝
 */
public class SpeedMachine {

    private int id;
    // 洗瓶机速度信号
    private double s1;
    // 酒机速度信号
    private double s2;
    // 杀菌机速度信号
    private double s3;
    // 贴标机速度信号
    private double s4;
    // 酒机计数
    private int q1;
    // 贴标机计数
    private int q2;
    // 时间
    private Date sptime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getS1() {
        return s1;
    }

    public void setS1(double s1) {
        this.s1 = s1;
    }

    public double getS2() {
        return s2;
    }

    public void setS2(double s2) {
        this.s2 = s2;
    }

    public double getS3() {
        return s3;
    }

    public void setS3(double s3) {
        this.s3 = s3;
    }

    public double getS4() {
        return s4;
    }

    public void setS4(double s4) {
        this.s4 = s4;
    }

    public int getQ1() {
        return q1;
    }

    public void setQ1(int q1) {
        this.q1 = q1;
    }

    public int getQ2() {
        return q2;
    }

    public void setQ2(int q2) {
        this.q2 = q2;
    }

    public Date getSptime() {
        return sptime;
    }

    public void setSptime(Date sptime) {
        this.sptime = sptime;
    }

}
