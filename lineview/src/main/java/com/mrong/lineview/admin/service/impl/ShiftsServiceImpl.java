package com.mrong.lineview.admin.service.impl;

import java.util.List;

import com.mrong.lineview.admin.dao.ShiftDao;
import com.mrong.lineview.admin.service.ShiftsService;
import com.mrong.lineview.common.entity.Shift;

public class ShiftsServiceImpl implements ShiftsService {

	private ShiftDao shiftDao;

	public void setShiftDao(ShiftDao shiftDao) {
		this.shiftDao = shiftDao;
	}

	@Override
	public void add(Shift s) {
		shiftDao.addShift(s);

	}

	@Override
	public void delete(Shift s) {
		shiftDao.deleteShift(s.getId());

	}

	@Override
	public List<Shift> get() {
		List<Shift> shifts = shiftDao.getShift();
		return shifts;
	}

	@Override
	public void edit(Shift s) {
		shiftDao.editShift(s);
	}

}
