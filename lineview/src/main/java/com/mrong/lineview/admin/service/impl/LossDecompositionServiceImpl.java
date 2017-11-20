package com.mrong.lineview.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.admin.service.LossDecompositionService;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.MathUtils;
import com.mrong.lineview.util.TimeUtils;

public class LossDecompositionServiceImpl implements LossDecompositionService {

    //时间计算
    private LossOfSpeedDao lossOfSpeedDao;

    public void setLossOfSpeedDao(LossOfSpeedDao lossOfSpeedDao) {
        this.lossOfSpeedDao = lossOfSpeedDao;
    }

    /**
     * 六大损失分解
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Entry<String, Double>> lossDecomposition(Date startTime, Date stopTime) {
        List<Entry<String, Double>> result = new ArrayList<>();

        double duration = TimeUtils.getDisparityOfSecond(startTime, stopTime);// 总时间

        double productionTime = lossOfSpeedDao.getProductionTime(startTime, stopTime);// 生产
        double scheduledDownTime = (long) lossOfSpeedDao.getScheduledDownTime(startTime, stopTime).get("duration");// 计划停机
        double externalLossTime = lossOfSpeedDao.getExternalLossTime(startTime, stopTime);// 外部损失
        double noProductionDemand = lossOfSpeedDao.getNoProductionDemand(startTime, stopTime);// 无生产需求

        result.add(new Entry<String, Double>("生产", MathUtils.formatTwoBit((double) (productionTime / duration))));// 生产
        result.add(new Entry<String, Double>("计划停机", MathUtils.formatTwoBit((double) (scheduledDownTime / duration))));// 计划停机
        result.add(new Entry<String, Double>("外部损失", MathUtils.formatTwoBit((double) (externalLossTime / duration))));// 外部损失
        result.add(
                new Entry<String, Double>("无生产需求", MathUtils.formatTwoBit((double) (noProductionDemand / duration))));// 无生产需求

        result.addAll(lossOfSpeedDao.getBigStopOfFillingMachine(startTime, stopTime));// 大停机、小停机
        // 速度损失

        return result;
    }
}
