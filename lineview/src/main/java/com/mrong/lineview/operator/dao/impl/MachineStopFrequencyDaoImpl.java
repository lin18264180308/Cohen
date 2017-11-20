package com.mrong.lineview.operator.dao.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.operator.dao.IMachineStopFrequencyDao;

/**
 * 计算机台停机频率和持续时间
 * 
 * @author 林金成
 *         2017年11月7日
 */
public class MachineStopFrequencyDaoImpl extends BaseDaoImpl implements IMachineStopFrequencyDao {

    /**
     * 计算机台某范围内停机持续时间
     */
    public double getMachineStopFrequency(Date beginTime, Date endTime, String machineName, String color) {
        String sql = "select begin_time, end_time from  mechine_stop_information where color = :color and machine_name = :machineName and (end_time between :beginTime and :endTime) "
                + "union all "
                + "select begin_time, end_time from mechine_stop_information where color = :color and machine_name = :machineName and end_time IS NULL and id ="
                + "(select max(id) from mechine_stop_information where machine_name = :machineName and color = :color);";
        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("machineName", machineName);
        propertyMap.put("beginTime", beginTime);
        propertyMap.put("endTime", endTime);
        propertyMap.put("color", color);
        List<?> data = this.getBySql(sql, propertyMap);

        return getDuration(data);
    }

    @SuppressWarnings("unchecked")
    private double getDuration(List<?> data) {
        double result = 0;
        List<Object[]> list = (List<Object[]>) data;
        Date begin = null;
        Date end = null;
        long milliseconds = 0;
        for (Object[] objs : list) {
            begin = (Date) objs[0];
            end = (Date) objs[1] == null ? new Date() : (Date) objs[1];// 若为空，设为当前时间
            milliseconds += end.getTime() - begin.getTime();
        }
        result = milliseconds / 60000;// 换算成分钟
        return result;
    }

    /**
     * 获取机台某一故障点的持续时间
     * 分析EBI前20秒内是否触发堵瓶信号
     */
    public boolean getMachineFaultDuration(String resultInfo, Date beginTime, Date endTime) {
        boolean b = false;
        String sql = "SELECT COUNT(id) FROM mechine_selfresult_information WHERE result_info = :resultInfo AND ((end_time IS NOT NULL AND end_time BETWEEN :beginTime AND :endTime) OR (end_time IS NULL));";

        Map<String, Object> propertyMap = new HashMap<>();
        propertyMap.put("resultInfo", resultInfo);
        propertyMap.put("beginTime", beginTime);
        propertyMap.put("endTime", endTime);
        List<?> data = this.getBySql(sql, propertyMap);
        if (data != null) {
            BigInteger count = (BigInteger) data.get(0);
            if (count.intValue() > 0) {
                b = true;
            }
        }
        return b;
    }
}
