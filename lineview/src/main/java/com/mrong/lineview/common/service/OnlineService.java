package com.mrong.lineview.common.service;

import java.util.List;

import com.mrong.lineview.common.entity.Online;

/**
 * 整线一览service接口
 * 
 * @author 张裕宝
 */
public interface OnlineService {

    /*
     * 获得当前状态方法
     */
    public Online getState();
    /*
     * 获取机台状态（初始化）
     */
    public List<Integer> initStatus(String machine_name);
    public Integer currentStatus(String machine_name);
}
