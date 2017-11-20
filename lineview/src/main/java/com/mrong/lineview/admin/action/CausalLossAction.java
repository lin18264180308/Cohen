package com.mrong.lineview.admin.action;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.admin.service.CausalLossService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Entry;
import com.mrong.lineview.util.TimeUtils;

/**
 * 因果损失报表
 * 
 * @author 林金成
 *         2017年11月16日
 */
public class CausalLossAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -821594124377315353L;

    private CausalLossService causalLossService;

    /**
     * 因果损失
     */
    private List<Entry<String, Double>> causalLoss;

    private String begin;
    private String end;

    /**
     * 因果损失分析报表数据加载方法
     */
    public String loadData() {
        Date startTime = TimeUtils.toDate(begin);
        Date stopTime = TimeUtils.toDate(end);
        /** 计算因果损失 **/
        causalLoss = causalLossService.causalLoss(startTime, stopTime);

        return JSON;
    }

    /** -----------------------------getter and setter------------------------------------- **/

    public List<Entry<String, Double>> getCausalLoss() {
        return causalLoss;
    }

    public void setCausalLossService(CausalLossService causalLossService) {
        this.causalLossService = causalLossService;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }
}
