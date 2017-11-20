package com.mrong.lineview.admin.service;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.entity.Entry;

/**
 * 速度损失
 * 
 * @author 林金成
 *         2017年11月16日
 */
public interface SpeedLossAreaService {
    /**
     * 速度区域
     */
    public List<Entry<String, Double>> speedArea(Date startTime, Date stopTime);
}
