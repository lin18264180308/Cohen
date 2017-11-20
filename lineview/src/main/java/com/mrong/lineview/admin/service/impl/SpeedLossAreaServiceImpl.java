package com.mrong.lineview.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.admin.service.SpeedLossAreaService;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.Machines;
import com.mrong.lineview.util.MathUtils;
import com.mrong.lineview.util.TimeUtils;

/**
 * 速度损失
 * 
 * @author 林金成
 *         2017年11月16日
 */
public class SpeedLossAreaServiceImpl implements SpeedLossAreaService {

    //时间计算
    private LossOfSpeedDao lossOfSpeedDao;

    public void setLossOfSpeedDao(LossOfSpeedDao lossOfSpeedDao) {
        this.lossOfSpeedDao = lossOfSpeedDao;
    }

    /**
     * 速度区域
     */
    public List<Entry<String, Double>> speedArea(Date startTime, Date stopTime) {

        //查询的结束时间减去查询的开始时间，单位为每秒得到的结果是一段时间内每秒的平均速度
        double speedTime = TimeUtils.calculate(stopTime, startTime);
        Map<String, Double> map = lossOfSpeedDao.getSpeed(startTime, stopTime);
        //洗瓶机查询时间段内的平均速度(乘以三是因为客户端三秒采集一次，乘以3600换算成每小时的平均速度)
        double bottleWasherSpeed = map.get("bottleWasher") * 3 * 3600 / speedTime;
        bottleWasherSpeed = MathUtils.formatTwoBit(bottleWasherSpeed);

        //灌酒机查询时间段内的平均速度
        double fillingMachineSpeed = map.get("fillingMachine") * 3 * 3600 / speedTime;
        fillingMachineSpeed = MathUtils.formatTwoBit(fillingMachineSpeed);

        //杀菌机查询时间段内的平均速度
        double sterilizerMachineSpeed = map.get("sterilizerMachine") * 3 * 3600 / speedTime;
        sterilizerMachineSpeed = MathUtils.formatTwoBit(sterilizerMachineSpeed);

        //贴标机查询时间内的平均速度
        double labelingMachine = map.get("labelingMachine") * 3 * 3600 / speedTime;
        labelingMachine = MathUtils.formatTwoBit(labelingMachine);
        double lossOfSpeedTime = 0;
        if (map.get("fillingMachine") > 0) {
            //损失时间单位为秒
            lossOfSpeedTime = ((map.get("fillingMachineCount") / (map.get("fillingMachine") * 3)) / speedTime)
                    - ((map.get("fillingMachineCount") * 3600) / 40000);
        }

        lossOfSpeedTime = MathUtils.formatTwoBit(lossOfSpeedTime);

        //总损失时间（公式=（查询时间-无生产需求时间）-总产量/40000瓶每小时）单位
        double totalLossTime = speedTime - map.get("noProductionDemandTime")
                - map.get("fillingMachineCount") * 3600 / 40000;
        totalLossTime = MathUtils.formatTwoBit(totalLossTime);

        //速度损失比：损失时间/总损失时间
        double velocityLossRatio = lossOfSpeedTime / totalLossTime;
        velocityLossRatio = MathUtils.formatTwoBit(velocityLossRatio);

        List<Entry<String, Double>> list = new ArrayList<>();
        list.add(new Entry<String, Double>(Machines.BOTTLEWASHINGMACHINE.getName(), bottleWasherSpeed));
        list.add(new Entry<String, Double>(Machines.FILLINGMACHINE.getName(), fillingMachineSpeed));
        list.add(new Entry<String, Double>(Machines.STERILIZATIONMACHINE.getName(), sterilizerMachineSpeed));
        list.add(new Entry<String, Double>(Machines.LABELINGMACHINE.getName(), labelingMachine));
        list.add(new Entry<String, Double>("速度损失时间", lossOfSpeedTime));
        list.add(new Entry<String, Double>("速度损失率", velocityLossRatio));

        return list;
    }
}
