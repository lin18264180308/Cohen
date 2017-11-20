package com.mrong.lineview.admin.service;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.entity.Entry;

/**
 * 六大损失分解
 * 
 * @author 林金成
 *         2017年11月16日
 */
public interface LossDecompositionService {
    /**
     * 六大损失分解
     * 
     * @param startTime
     * @param stopTime
     */
    public List<Entry<String, Double>> lossDecomposition(Date startTime, Date stopTime);
}
