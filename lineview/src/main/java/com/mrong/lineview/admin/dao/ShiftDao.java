package com.mrong.lineview.admin.dao;

import java.util.List;

import com.mrong.lineview.common.entity.Shift;

public interface ShiftDao {

	/**
	 * 增
	 */
	public void addShift(Shift s);

	/**
	 * 删
	 */
	public void deleteShift(int id);

	/**
	 * 查
	 */
	public List<Shift> getShift();

	/**
	 * 改
	 */
	public void editShift(Shift s);

}
