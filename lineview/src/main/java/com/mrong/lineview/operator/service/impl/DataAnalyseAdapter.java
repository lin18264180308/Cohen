package com.mrong.lineview.operator.service.impl;

import com.mrong.lineview.common.entity.ResultDataBean;
import com.mrong.lineview.operator.service.IStatusViewService;

/**
 * 数据处理适配器，满足多个action共享一个service的需求
 * 
 * @author 林金成
 *         2017年10月27日
 */
@SuppressWarnings("all")
public class DataAnalyseAdapter {

    private IStatusViewService statusViewServiceImpl;

    public void setStatusViewServiceImpl(IStatusViewService statusViewServiceImpl) {
        this.statusViewServiceImpl = statusViewServiceImpl;
    }

    public ResultDataBean getStatus() {
        return statusViewServiceImpl.getStatus();
    }
}
