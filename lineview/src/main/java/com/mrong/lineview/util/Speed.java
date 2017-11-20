package com.mrong.lineview.util;

/**
 * 四大机台额定速度
 * 
 * @author 林金成
 *         2017年10月27日
 */
public enum Speed {
    BottleWasher(48000), FillingMachine(40000), SterilizationMachine(45000), LabelingMachine(20000);

    private double speed;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    private Speed(double speed) {
        this.speed = speed;
    }
}
