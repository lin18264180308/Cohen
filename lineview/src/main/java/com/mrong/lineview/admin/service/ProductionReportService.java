package com.mrong.lineview.admin.service;

import java.util.Date;

public interface ProductionReportService {

	/**
	 * 获得查询时间内灌酒机产生的瓶数
	 */
	public double getFillingMachineCount(Date startTime, Date stopTime);

}
