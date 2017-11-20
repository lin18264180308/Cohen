package com.mrong.lineview.operator.service;

import com.mrong.lineview.operator.entity.OnlineCheck;

public interface OnlineCheckService {
    /**
     * 保存方法
     * 
     * @param o
     *            线上检查表对象
     */
    public void saveOnlineCheck(OnlineCheck o);

    /**
     * 自动获取plc的数据
     */
    public Object[] getStatus();

}
