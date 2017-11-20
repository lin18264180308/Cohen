package com.mrong.lineview.admin.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.service.SpeedLossAreaService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.Machines;
import com.mrong.lineview.util.Speed;
import com.mrong.lineview.util.TimeUtils;

/**
 * 速度损失
 * 
 * @author 林金成
 *         2017年11月16日
 */
public class SpeedLossAreaAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = -7394489410628286240L;

    private SpeedLossAreaService SpeedLossAreaService;

    /**
     * 速度区域
     */
    private List<Entry<String, Double>> speedArea;

    /**
     * 额定速度
     */
    private List<Entry<String, Double>> ratedSpeed;

    /**
     * 时间参数
     */
    private String begin;
    private String end;

    /**
     * 因果损失分析报表数据加载方法
     */
    public String loadData() {
        Date startTime = TimeUtils.toDate(begin);
        Date stopTime = TimeUtils.toDate(end);
        /** 计算速度区域 **/
        speedArea = SpeedLossAreaService.speedArea(startTime, stopTime);
        // 填充额定速度
        fillRatedSpeed(ratedSpeed);

        return JSON;
    }

    /**
     * 填充额定速度
     * 
     * @param data
     */
    private void fillRatedSpeed(List<Entry<String, Double>> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        data.add(new Entry<String, Double>(Machines.BOTTLEWASHINGMACHINE.getName(), Speed.BottleWasher.getSpeed()));
        data.add(new Entry<String, Double>(Machines.FILLINGMACHINE.getName(), Speed.FillingMachine.getSpeed()));
        data.add(new Entry<String, Double>(Machines.STERILIZATIONMACHINE.getName(),
                Speed.SterilizationMachine.getSpeed()));
        data.add(new Entry<String, Double>(Machines.LABELINGMACHINE.getName(), Speed.LabelingMachine.getSpeed()));
    }

    /** ------------------------------getter and setter------------------------------------------ **/

    public List<Entry<String, Double>> getSpeedArea() {
        return speedArea;
    }

    public void setSpeedLossAreaService(SpeedLossAreaService speedLossAreaService) {
        SpeedLossAreaService = speedLossAreaService;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public List<Entry<String, Double>> getRatedSpeed() {
        return ratedSpeed;
    }
}
