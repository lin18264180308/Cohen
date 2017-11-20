package com.mrong.lineview.operator.service.impl;

import com.mrong.lineview.operator.dao.OnlineCheckDao;
import com.mrong.lineview.operator.entity.OnlineCheck;
import com.mrong.lineview.operator.service.OnlineCheckService;

/**
 * 线上检查表逻辑层
 * 
 * @author 张裕宝
 */
public class OnlineCheckServiceImpl implements OnlineCheckService {

    private OnlineCheckDao onlineCheckDao;

    public void setOnlineCheckDao(OnlineCheckDao onlineCheckDao) {
        this.onlineCheckDao = onlineCheckDao;
    }

    /**
     * 保存方法
     */
    public void saveOnlineCheck(OnlineCheck o) {
        onlineCheckDao.saveOnlineCheck(o);

    }

    /**
     * 自动获取plc
     */
    public Object[] getStatus() {
        Object[] objects = onlineCheckDao.getStaus();
        return objects;
    }

}
