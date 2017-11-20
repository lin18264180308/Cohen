package com.mrong.lineview.operator.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mrong.lineview.operator.dao.ThreeMajorIndicatorsDao;
import com.mrong.lineview.operator.service.ThreeMajorIndicatorsService;

public class ThreeMajorIndicatorsServiceImpl implements ThreeMajorIndicatorsService {

    private ThreeMajorIndicatorsDao threeMajorIndicatorsDao;

    public void setThreeMajorIndicatorsDao(ThreeMajorIndicatorsDao threeMajorIndicatorsDao) {
        this.threeMajorIndicatorsDao = threeMajorIndicatorsDao;
    }

    @Override
    public Map<String, Double> indexCalculation(Date startTime, Date stopTime) {
        Map<String, Double> map = new HashMap<>();
        Map<String, Double> map1 = new HashMap<>();
        map = threeMajorIndicatorsDao.indexCalculation(startTime, stopTime);
        double oee = map.get("ept") * 100 / map.get("lt");
        double gly = map.get("ept") * 100 / map.get("st");
        double lef = map.get("ept") * 100 / map.get("let");
        map1.put("oee", oee);
        map1.put("gly", gly);
        map1.put("lef", lef);
        return map1;
    }
}
