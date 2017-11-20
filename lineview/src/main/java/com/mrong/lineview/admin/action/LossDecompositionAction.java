package com.mrong.lineview.admin.action;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.service.LossDecompositionService;
import com.mrong.lineview.admin.service.SpeedLossAreaService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.TimeUtils;

/**
 * 六大损失分解
 * 
 * @author 林金成
 *         2017年11月16日
 */
public class LossDecompositionAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = 3327612940872413781L;

    private LossDecompositionService lossDecompositionService;
    private SpeedLossAreaService speedLossAreaService;
    /**
     * 六大损失分解
     */
    private List<Entry<String, Double>> lossDecomposition;

    private String begin;
    private String end;

    /**
     * 因果损失分析报表数据加载方法
     */
    public String loadData() {
        Date startTime = TimeUtils.toDate(begin);
        Date stopTime = TimeUtils.toDate(end);

        /** 六大损失分解 **/
        lossDecomposition = lossDecompositionService.lossDecomposition(startTime, stopTime);
        lossDecomposition.add(new Entry<String, Double>("速度损失",
                speedLossAreaService.speedArea(startTime, stopTime).get(4).getValue()));// 速度损失时间

        return JSON;
    }

    /** ------------------------------getter and setter------------------------------------------ **/

    public List<Entry<String, Double>> getLossDecomposition() {
        return lossDecomposition;
    }

    public void setLossDecompositionService(LossDecompositionService lossDecompositionService) {
        this.lossDecompositionService = lossDecompositionService;
    }

    public void setSpeedLossAreaService(SpeedLossAreaService speedLossAreaService) {
        this.speedLossAreaService = speedLossAreaService;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
