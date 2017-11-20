package com.mrong.lineview.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.admin.service.ScheduledDownTimeService;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.MathUtils;

@SuppressWarnings("all")
public class ScheduledDownTimeServiceImpl implements ScheduledDownTimeService {

    //时间计算
    private LossOfSpeedDao lossOfSpeedDao;

    public void setLossOfSpeedDao(LossOfSpeedDao lossOfSpeedDao) {
        this.lossOfSpeedDao = lossOfSpeedDao;
    }

    /**
     * 计划停机状态统计
     * (计划停机时间/计划生产时间)*(1-GLY)
     */
    public List<Entry<String, Double>> scheduledDownTime(Date startTime, Date stopTime) {

        List<Entry<String, Double>> list = new ArrayList<>();
        Map<String, Object> data = lossOfSpeedDao.getScheduledDownTime(startTime, stopTime);
        // 计划停机的所有状态
        Map<String, Long> scheduledDown = (Map<String, Long>) data.get("scheduledDown");
        // 计划生产时间
        double st = lossOfSpeedDao.getPlanTime(startTime, stopTime);
        // ept有效生产时间
        double ept = lossOfSpeedDao.getEpt(startTime, stopTime);
        ept = MathUtils.formatTwoBit(ept);
        double gly = 0.0;
        if (st != 0) {
            gly = (ept * 100) / st;
            gly = MathUtils.formatTwoBit(gly);
        }
        if (scheduledDown != null && scheduledDown.size() > 0) {
            for (String key : scheduledDown.keySet()) {
                double value = 0.0;
                if (st != 0) {
                    double dura = scheduledDown.get(key);
                    value = (dura / st) * (1 - gly);
                    value = MathUtils.formatTwoBit(value);
                }
                list.add(new Entry<String, Double>(key, value));
            }
        }

        return list;
    }
}
