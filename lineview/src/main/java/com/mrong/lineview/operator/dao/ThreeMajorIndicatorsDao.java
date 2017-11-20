package com.mrong.lineview.operator.dao;

import java.util.Date;
import java.util.Map;

import com.mrong.lineview.common.dao.IBaseDao;

/**
 * 整线一览三大指标
 * 
 * @author 张裕宝
 */
public interface ThreeMajorIndicatorsDao extends IBaseDao {
    public Map<String, Double> indexCalculation(Date startTime, Date stopTime);

}
