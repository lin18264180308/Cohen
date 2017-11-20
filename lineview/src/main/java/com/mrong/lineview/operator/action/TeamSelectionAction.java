package com.mrong.lineview.operator.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Shift;
import com.mrong.lineview.common.entity.Team;
import com.mrong.lineview.operator.entity.WorkerRecord;
import com.mrong.lineview.operator.service.TeamSelectionService;

@SuppressWarnings("all")
public class TeamSelectionAction extends BaseAction {

	private TeamSelectionService teamSelectionService;
	// 班次
	private Integer shiftid;
	// 班组
	private Integer teamid;

	public Integer getShiftid() {
		return shiftid;
	}

	public void setShiftid(Integer shiftid) {
		this.shiftid = shiftid;
	}

	public Integer getTeamid() {
		return teamid;
	}

	public void setTeamid(Integer teamid) {
		this.teamid = teamid;
	}

	private List<Shift> shifts;
	private List<Integer> teams;

	public void setTeamSelectionService(TeamSelectionService teamSelectionService) {
		this.teamSelectionService = teamSelectionService;
	}

	public List<Integer> getTeams() {
		return teams;
	}

	public void setTeams(List<Integer> teams) {
		this.teams = teams;
	}

	public List<Shift> getShifts() {
		return shifts;
	}

	public void setShifts(List<Shift> shifts) {
		this.shifts = shifts;
	}

	/*
	 * 获取所有班次方法,获取所有班组的方法
	 */
	public String getAll() {
		shifts = teamSelectionService.getAll();
		List<Team> team = teamSelectionService.getTeam();
		teams = new ArrayList<>();
		for (int i = 0; i < team.size(); i++) {
			teams.add(team.get(i).getId());
		}
		return "json";
	}

	public String teamSave() {
		WorkerRecord workerRecord = new WorkerRecord();
		workerRecord.setShiftId(shiftid);
		workerRecord.setTeamId(teamid);
		workerRecord.setDate(new Date());
		teamSelectionService.saveWr(workerRecord);
		application.put("teamid", teamid);
		return "success";
	}

}
