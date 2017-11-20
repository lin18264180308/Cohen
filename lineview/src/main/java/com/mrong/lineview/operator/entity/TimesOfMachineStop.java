package com.mrong.lineview.operator.entity;

/**
 * 机台大小停机信息
 * 
 * @author 林金成
 *         2017年10月26日
 */
public class TimesOfMachineStop {

    private int timesOfBigStop;// 大停机次数
    private int timesOfSmallStop;// 小停机次数
    private int durationOfBigStop;// 大停机总持续时间
    private int durationOfSmallStop;// 小停机总持续时间

    public TimesOfMachineStop() {
    }

    public TimesOfMachineStop(int timesOfBigStop, int timesOfSmallStop, int durationOfBigStop,
            int durationOfSmallStop) {
        this.timesOfBigStop = timesOfBigStop;
        this.timesOfSmallStop = timesOfSmallStop;
        this.durationOfBigStop = durationOfBigStop;
        this.durationOfSmallStop = durationOfSmallStop;
    }

    public int getTimesOfBigStop() {
        return timesOfBigStop;
    }

    public void setTimesOfBigStop(int timesOfBigStop) {
        this.timesOfBigStop = timesOfBigStop;
    }

    public int getTimesOfSmallStop() {
        return timesOfSmallStop;
    }

    public void setTimesOfSmallStop(int timesOfSmallStop) {
        this.timesOfSmallStop = timesOfSmallStop;
    }

    public int getDurationOfBigStop() {
        return durationOfBigStop;
    }

    public void setDurationOfBigStop(int durationOfBigStop) {
        this.durationOfBigStop = durationOfBigStop;
    }

    public int getDurationOfSmallStop() {
        return durationOfSmallStop;
    }

    public void setDurationOfSmallStop(int durationOfSmallStop) {
        this.durationOfSmallStop = durationOfSmallStop;
    }
}
