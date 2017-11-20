package com.mrong.lineview.common.dao;

import java.util.List;

import com.mrong.lineview.common.entity.HaltBean;
import com.mrong.lineview.common.entity.Online;

/**
 * 整线一览，四大机台的停机分类相关
 * 
 * @author 张裕宝
 */
public interface HaltDao {
    /*
     * 获得整线一览状态
     */
    public Online getState();

    // 从online历史记录表中获取最新的一条记录
    public List<Object[]> getLast();

    // 获取洗瓶机/灌酒机/杀菌机/贴标机
    public List<HaltBean> getMachine(String message);

    // 增洗瓶机/灌酒机/杀菌机/贴标机
    public void saveMachine(HaltBean h);

    // 获取上一条记录的时间
    public HaltBean getTime(String message);

    // 获取全部记录
    public List<HaltBean> getALL(String message);

    // 更新上一条记录
    public void updateMachine(HaltBean h);
}
