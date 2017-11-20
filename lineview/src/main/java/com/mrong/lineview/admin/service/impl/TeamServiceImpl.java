package com.mrong.lineview.admin.service.impl;

import java.util.List;

import com.mrong.lineview.admin.dao.TeamDao;
import com.mrong.lineview.admin.service.TeamService;
import com.mrong.lineview.common.entity.Team;

public class TeamServiceImpl implements TeamService {

	private TeamDao teamDao;

	public void setTeamDao(TeamDao teamDao) {
		this.teamDao = teamDao;
	}

	@Override
	public void edit(int banzu, int userid) {
		teamDao.edit(banzu, userid);

	}

	@Override
	public List<Team> getAll() {
		List<Team> teams = teamDao.getAll();
		return teams;
	}

}
