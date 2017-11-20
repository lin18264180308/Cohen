package com.mrong.lineview.operator.dao;

import java.util.List;

import com.mrong.lineview.common.dao.IBaseDao;
import com.mrong.lineview.common.entity.Shift;
import com.mrong.lineview.common.entity.Team;
import com.mrong.lineview.operator.entity.WorkerRecord;

/**
 * 班次选择接口
 * 
 * @author 张裕宝
 */
public interface TeamSelectionDao extends IBaseDao {

    /*
     * 获得所有的班次的抽象方法
     */
    public List<Shift> getAll();

    /*
     * 获得所有的班组
     */
    public List<Team> getteamAll();

    /*
     * 保存选择信息
     */
    public void saveWorkerRecord(WorkerRecord w);
}
