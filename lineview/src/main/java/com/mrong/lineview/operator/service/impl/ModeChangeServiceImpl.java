package com.mrong.lineview.operator.service.impl;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.entity.Mode;
import com.mrong.lineview.operator.dao.ModeChangeDao;
import com.mrong.lineview.operator.entity.ModeRecord;
import com.mrong.lineview.operator.service.ModeChangeService;
import com.mrong.lineview.util.TimeUtils;

public class ModeChangeServiceImpl implements ModeChangeService {

	private ModeChangeDao modeChangeDao;

	public ModeChangeDao getModeChangeDao() {
		return modeChangeDao;
	}

	public void setModeChangeDao(ModeChangeDao modeChangeDao) {
		this.modeChangeDao = modeChangeDao;
	}

	/*
	 * 获取所有模式方法
	 * @see com.mrong.lineview.operator.service.ModeChangeService#getAll()
	 */
	@Override
	public List<Mode> getAll() {
		List<Mode> mode = modeChangeDao.get();
		return mode;
	}

	/*
	 * (切换模式后要执行的操作，先查询最新的一条消息获取开始时间得到持续时间存入数据库，然后再存一条新记录到数据库)
	 * @see
	 * com.mrong.lineview.operator.service.ModeChangeService#save(com.mrong.lineview
	 * .operator.entity.ModeRecord)
	 */
	public String modeChange(ModeRecord m) {
		ModeRecord modeRecord = modeChangeDao.getTime();
		if (modeRecord == null) {
			System.out.println("获取为空");
			modeChangeDao.saveModeChange(m);
		} else {
			// 计算出持续时间
			long duration = TimeUtils.calculate(new Date(), modeRecord.getStartTime());
			modeRecord.setDuration(duration);
			modeRecord.setStopTime(new Date());
			// 更新持续时间,停止时间
			modeChangeDao.updateTime(modeRecord);
			// 保存当前记录
			modeChangeDao.saveModeChange(m);
		}
		return "success";
	}

	/**
	 * 获取所有的品种信息
	 */
	@SuppressWarnings("unchecked")
	public List<String> getVarietie() {
		String sql = "SELECT name FROM varieties";
		List<String> data = (List<String>) modeChangeDao.getBySql(sql, null);
		return data;
	}

}
