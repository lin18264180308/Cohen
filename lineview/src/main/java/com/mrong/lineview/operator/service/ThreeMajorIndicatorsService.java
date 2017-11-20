package com.mrong.lineview.operator.service;

import java.util.Date;
import java.util.Map;

public interface ThreeMajorIndicatorsService {

    public Map<String, Double> indexCalculation(Date startTime, Date stopTime);

}
