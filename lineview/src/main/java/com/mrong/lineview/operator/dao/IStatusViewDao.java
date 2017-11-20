package com.mrong.lineview.operator.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.dao.IBaseDao;
import com.mrong.lineview.operator.entity.MechineStopInformationBean;

public interface IStatusViewDao extends IBaseDao {
    public int getAvgSpeedOfMachine(List<?> list, int duration, double xishu);

    public Map<String, Integer> getStopTimesOfMachine(List<String> keys, List<?> values);

    public List<MechineStopInformationBean> getStopInfomationOfMachine(String sql, Map<String, Object> propertyMap);

    public Date getStartTime();

    public Date getMachineStopTime(String sql, Map<String, Object> propertyMap);

    public Map<String, Integer> getPointsOfMachine(String sql, List<String> keys);
}
