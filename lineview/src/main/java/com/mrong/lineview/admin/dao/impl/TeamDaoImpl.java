package com.mrong.lineview.admin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.TeamDao;
import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.Team;

@SuppressWarnings("all")
public class TeamDaoImpl extends BaseDaoImpl implements TeamDao {

    public void edit(int banzu, int userid) {
        String sql = "UPDATE team SET  id = :banzu WHERE user_id=:userid";
        Map<String, Object> map = new HashMap<>();
        map.put("banzu", banzu);
        map.put("userid", userid);
        this.executeBySql(sql, map);
    }

    /**
     * 获取所有班组的全部信息
     */
    public List<Team> getAll() {
        String hql = "FROM Team t LEFT OUTER JOIN FETCH t.user";
        List<Team> teams = (List<Team>) this.getByHql(hql, null);
        if (teams == null || teams.size() == 0) {
            teams = null;
        }
        return teams;
    }

}
