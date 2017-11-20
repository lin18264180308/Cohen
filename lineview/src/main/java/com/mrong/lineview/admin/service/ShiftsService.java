package com.mrong.lineview.admin.service;

import java.util.List;

import com.mrong.lineview.common.entity.Shift;

public interface ShiftsService {
	public void add(Shift s);

	public void delete(Shift s);

	public List<Shift> get();

	public void edit(Shift s);

}
