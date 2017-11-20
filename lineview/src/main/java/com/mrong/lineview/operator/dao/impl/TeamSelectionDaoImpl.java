package com.mrong.lineview.operator.dao.impl;

import java.util.List;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.Shift;
import com.mrong.lineview.common.entity.Team;
import com.mrong.lineview.operator.dao.TeamSelectionDao;
import com.mrong.lineview.operator.entity.WorkerRecord;
@SuppressWarnings("all")
public class TeamSelectionDaoImpl extends BaseDaoImpl implements TeamSelectionDao {

    /*
     * 获取所有班次时间方法
     * @see com.mrong.lineview.operator.dao.TeamSelectionDao#getAll()
     */
    public List<Shift> getAll() {
        String hql = "FROM Shift";
        List<Shift> shifts = getSession().createQuery(hql).list();
        return shifts;
    }

    /*
     * 获得所有班组的方法(non-Javadoc)
     * @see com.mrong.lineview.operator.dao.TeamSelectionDao#getteamAll()
     */
    public List<Team> getteamAll() {
        String hql = "FROM Team";
        List<Team> teams = getSession().createQuery(hql).list();
        return teams;
    }

    @Override
    public void saveWorkerRecord(WorkerRecord w) {
        super.save(w);

    }

}
