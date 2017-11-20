package com.mrong.lineview.operator.service;

import java.util.Map;

public interface ICurrentBreakDownService {
    public Map<String, Long> currentFaultInfo(String machineFlg, String startTime);
}
