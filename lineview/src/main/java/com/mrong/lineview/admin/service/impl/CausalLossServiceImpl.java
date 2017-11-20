package com.mrong.lineview.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.admin.service.CausalLossService;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.Machines;
import com.mrong.lineview.util.MathUtils;

public class CausalLossServiceImpl implements CausalLossService {
    //时间计算
    private LossOfSpeedDao lossOfSpeedDao;

    public void setLossOfSpeedDao(LossOfSpeedDao lossOfSpeedDao) {
        this.lossOfSpeedDao = lossOfSpeedDao;
    }

    /**
     * 因果损失
     * (因果设备的停机时间/计划生产时间)*(1-GLY)
     */
    public List<Entry<String, Double>> causalLoss(Date startTime, Date stopTime) {

        // 计划生产时间
        double st = lossOfSpeedDao.getPlanTime(startTime, stopTime);
        // ept有效生产时间
        double ept = lossOfSpeedDao.getEpt(startTime, stopTime);
        ept = MathUtils.formatTwoBit(ept);

        // <机台名称,机台因果损失>
        List<Entry<String, Double>> list = new ArrayList<>();
        list.add(new Entry<String, Double>(Machines.BOTTLEWASHINGMACHINE.getName(),
                causalLossOfOne(startTime, stopTime, Machines.BOTTLEWASHINGMACHINE.getName(), st, ept)));
        list.add(new Entry<String, Double>(Machines.FILLINGMACHINE.getName(),
                causalLossOfOne(startTime, stopTime, Machines.FILLINGMACHINE.getName(), st, ept)));
        list.add(new Entry<String, Double>(Machines.STERILIZATIONMACHINE.getName(),
                causalLossOfOne(startTime, stopTime, Machines.STERILIZATIONMACHINE.getName(), st, ept)));
        list.add(new Entry<String, Double>(Machines.LABELINGMACHINE.getName(),
                causalLossOfOne(startTime, stopTime, Machines.LABELINGMACHINE.getName(), st, ept)));
        list.add(new Entry<String, Double>(Machines.LIANDAO1.getName(),
                causalLossOfOne(startTime, stopTime, Machines.LIANDAO1.getName(), st, ept)));
        list.add(new Entry<String, Double>(Machines.LIANDAO2.getName(),
                causalLossOfOne(startTime, stopTime, Machines.LIANDAO2.getName(), st, ept)));
        list.add(new Entry<String, Double>(Machines.LIANDAO3.getName(),
                causalLossOfOne(startTime, stopTime, Machines.LIANDAO3.getName(), st, ept)));
        list.add(new Entry<String, Double>(Machines.LIANDAO4.getName(),
                causalLossOfOne(startTime, stopTime, Machines.LIANDAO4.getName(), st, ept)));

        return list;
    }

    /**
     * 计算单机台的因果损失
     * (因果设备的停机时间/计划生产时间)*(1-GLY)
     */
    private double causalLossOfOne(Date startTime, Date stopTime, String machineName, double st, double ept) {
        double result = 0.0;

        // 因果设备的停机时间
        double durationOfWilte = lossOfSpeedDao.getMachineStopInWhite(startTime, stopTime, machineName);

        double gly = 0.0;
        if (st != 0) {
            gly = (ept * 100) / st;
            gly = MathUtils.formatTwoBit(gly);
        }

        if (st != 0) {
            result = (durationOfWilte / st) * (1 - gly);
            result = MathUtils.formatTwoBit(result);
        }

        return result;
    }
}
