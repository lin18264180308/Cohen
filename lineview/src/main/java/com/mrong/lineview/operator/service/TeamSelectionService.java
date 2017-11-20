package com.mrong.lineview.operator.service;

import java.util.List;

import com.mrong.lineview.common.entity.Shift;
import com.mrong.lineview.common.entity.Team;
import com.mrong.lineview.operator.entity.WorkerRecord;

/**
 * 抽象班次选择业务逻辑
 * 
 * @author 张裕宝
 */
public interface TeamSelectionService {

    /*
     * 抽象班次选择方法
     */
    public List<Shift> getAll();

    /*
     * 抽象班组选择方法
     */
    public List<Team> getTeam();

    /*
     * 保存选择条件
     */
    public void saveWr(WorkerRecord w);
}
