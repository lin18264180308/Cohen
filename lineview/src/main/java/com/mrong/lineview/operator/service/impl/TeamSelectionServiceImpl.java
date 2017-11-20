package com.mrong.lineview.operator.service.impl;

import java.util.List;

import com.mrong.lineview.common.entity.Shift;
import com.mrong.lineview.common.entity.Team;
import com.mrong.lineview.operator.dao.TeamSelectionDao;
import com.mrong.lineview.operator.entity.WorkerRecord;
import com.mrong.lineview.operator.service.TeamSelectionService;

/**
 * 班次选择业务逻辑具体实现
 * 
 * @author 张裕宝
 */
public class TeamSelectionServiceImpl implements TeamSelectionService {

    private TeamSelectionDao teamSelectionDao;

    public void setTeamSelectionDao(TeamSelectionDao teamSelectionDao) {
        this.teamSelectionDao = teamSelectionDao;
    }

    /*
     * 获取所有班次信息具体实现方法
     */
    public List<Shift> getAll() {
        List<Shift> shifts = teamSelectionDao.getAll();
        return shifts;
    }

    @Override
    public List<Team> getTeam() {
        List<Team> teams = teamSelectionDao.getteamAll();
        return teams;
    }

    @Override
    public void saveWr(WorkerRecord w) {
        teamSelectionDao.save(w);

    }

}
