package com.mrong.lineview.operator.dao.impl;

import java.util.List;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.operator.dao.OnlineCheckDao;
import com.mrong.lineview.operator.entity.OnlineCheck;

/**
 * 线上检查
 * 
 * @author 张裕宝
 */
public class OnlineCheckDaoImpl extends BaseDaoImpl implements OnlineCheckDao {

    /**
     * 线上检查表的保存方法
     */
    public void saveOnlineCheck(OnlineCheck o) {
        super.save(o);
    }

    /**
     * 线上检查表自动从数据库中提取数据
     */
    public Object[] getStaus() {
        String sql = "SELECT * FROM biaoming WHERE id=(SELECT max(id) FROM biaoming)";
        List<?> list = this.getBySql(sql, null);
        if (list == null || list.size() == 0) {
            return null;
        } else {
            Object[] objects = (Object[]) list.get(0);
            return objects;
        }

    }

}
