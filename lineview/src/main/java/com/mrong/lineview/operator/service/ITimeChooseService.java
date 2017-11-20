package com.mrong.lineview.operator.service;

import java.util.List;
import java.util.Map;

import com.mrong.lineview.operator.entity.TimeChooseParameterBean;
import com.mrong.lineview.operator.entity.TimeChooseStatusBean;

public interface ITimeChooseService {
    public Map<String, List<TimeChooseStatusBean>> getStatusWithTimeChoose(TimeChooseParameterBean params);
}
