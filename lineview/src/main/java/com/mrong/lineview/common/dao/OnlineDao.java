package com.mrong.lineview.common.dao;

import java.util.List;
import com.mrong.lineview.common.entity.Online;

/**
 * 整线一览接口（四大机台）
 * 
 * @author 张裕宝
 */
public interface OnlineDao {

    /*
     * 获得整线一览状态
     */
    public Online getState();
    /*
     * 获取机台实时状态（初始化）
     */
	public List<Integer> initStatus(String machine_name);
	public Integer currentStatus(String machine_name);

}
