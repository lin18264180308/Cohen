package com.mrong.lineview.operator.action;

import java.util.Map;

import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.operator.service.ICurrentBreakDownService;

public class CurrentBreakDownAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 6866011590275169460L;

    private ICurrentBreakDownService currentBreakDownService;

    public void setCurrentBreakDownService(ICurrentBreakDownService currentBreakDownService) {
        this.currentBreakDownService = currentBreakDownService;
    }

    private String machineFlg;
    private String startTime;

    public void setMachineFlg(String machineFlg) {
        this.machineFlg = machineFlg;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 当前故障名
     */
    private String faultName;
    /**
     * 故障开始的时间
     */
    private Long beginTime;

    public String getFaultName() {
        return faultName;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public String currentFaultInfo() {
        Map<String, Long> map = currentBreakDownService.currentFaultInfo(machineFlg, startTime);
        for (String key : map.keySet()) {
            faultName = key;
            beginTime = map.get(key);
        }
        return JSON;
    }
}
