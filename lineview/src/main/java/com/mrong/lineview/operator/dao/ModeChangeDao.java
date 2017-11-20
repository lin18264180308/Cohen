package com.mrong.lineview.operator.dao;

import java.util.List;

import com.mrong.lineview.common.dao.IBaseDao;
import com.mrong.lineview.common.entity.Mode;
import com.mrong.lineview.operator.entity.ModeRecord;

/**
 * 更改模式操作
 * 
 * @author 张裕宝
 */
public interface ModeChangeDao extends IBaseDao {
    // 获得所有模式
    public List<Mode> get();

    // 更新上一条记录
    public void updateTime(ModeRecord m);

    // 保存最新一条记录
    public void saveModeChange(ModeRecord modeRecord);

    // 查询上一条记录时间
    public ModeRecord getTime();
}
