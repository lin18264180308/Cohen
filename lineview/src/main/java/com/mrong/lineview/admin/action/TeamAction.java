package com.mrong.lineview.admin.action;

import java.util.List;

import com.mrong.lineview.admin.service.TeamService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Team;

public class TeamAction extends BaseAction {
    /**
     * 
     */
    private static final long serialVersionUID = -3192268100183064649L;

    int banzu;
    int userid;
    List<Team> list;
    private TeamService teamService;

    public int getBanzu() {
        return banzu;
    }

    public void setBanzu(int banzu) {
        this.banzu = banzu;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public List<Team> getList() {
        return list;
    }

    public void setList(List<Team> list) {
        this.list = list;
    }

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    /**
     * 获得所有班组信息的方法
     * 
     * @return
     */
    public String getAll() {
        list = teamService.getAll();
        return SUCCESS;
    }

    /**
     * 编辑班组的方法
     * 
     * @return
     */
    public String edit() {
        teamService.edit(banzu, userid);
        return SUCCESS;
    }

}
