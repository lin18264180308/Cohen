package com.mrong.lineview.admin.service.impl;

import java.util.Date;
import java.util.Map;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.admin.service.ProductionReportService;

public class ProductionReportServiceImpl implements ProductionReportService {

	private LossOfSpeedDao lossOfSpeedDao;

	public void setLossOfSpeedDao(LossOfSpeedDao lossOfSpeedDao) {
		this.lossOfSpeedDao = lossOfSpeedDao;
	}

	/**
	 * 获得查询时间内酒机的产量
	 */
	public double getFillingMachineCount(Date startTime, Date stopTime) {
		Map<String, Double> map = lossOfSpeedDao.getSpeed(startTime, stopTime);
		return map.get("fillingMachineCount");
	}

	/**
	 * 
	 */
}
