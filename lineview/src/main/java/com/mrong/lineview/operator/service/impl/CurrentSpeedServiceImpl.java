package com.mrong.lineview.operator.service.impl;

import java.util.List;

import com.mrong.lineview.operator.dao.ICurrentSpeedDao;
import com.mrong.lineview.operator.service.ICurrentSpeedService;

public class CurrentSpeedServiceImpl implements ICurrentSpeedService {

    private ICurrentSpeedDao currentSpeedDao;

    public void setCurrentSpeedDao(ICurrentSpeedDao currentSpeedDao) {
        this.currentSpeedDao = currentSpeedDao;
    }

    public double getCurrentSpeed(String machineFlg) {
        String sql = null;
        double xishu = 1.0;
        switch (machineFlg) {
        case "1":
            sql = "SELECT s1 from Real_time_speed;";
            xishu = 1.0;
            break;
        case "2":
            sql = "SELECT s2 from Real_time_speed;";
            xishu = 1.64;
            break;
        case "3":
            sql = "SELECT s3 from Real_time_speed;";
            xishu = 1000;
            break;
        case "4":
            sql = "SELECT s4 from Real_time_speed;";
            xishu = 1.0;
            break;
        default:
            sql = "SELECT s1 from Real_time_speed;";
            xishu = 1.0;
            break;
        }
        List<?> list = currentSpeedDao.getBySql(sql, null);
        Double result = (Double) list.get(0);
        if (result == null) {
            result = 0.0;
        }
        return (result * xishu);
    }
}
