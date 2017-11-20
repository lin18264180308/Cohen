package com.mrong.lineview.admin.dao;

import java.util.List;

import com.mrong.lineview.common.dao.IBaseDao;
import com.mrong.lineview.common.entity.Team;

public interface TeamDao extends IBaseDao {

    /**
     * 修改班组
     */
    public void edit(int banzu, int userid);

    /**
     * 获取所有班组
     */
    public List<Team> getAll();

}
