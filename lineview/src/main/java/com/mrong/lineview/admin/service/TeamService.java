package com.mrong.lineview.admin.service;

import java.util.List;

import com.mrong.lineview.common.entity.Team;

public interface TeamService {

	public void edit(int banzu, int userid);

	public List<Team> getAll();

}
