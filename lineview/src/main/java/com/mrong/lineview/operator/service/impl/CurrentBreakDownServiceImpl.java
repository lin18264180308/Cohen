package com.mrong.lineview.operator.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.operator.dao.ICurrentBreakDownDao;
import com.mrong.lineview.operator.service.ICurrentBreakDownService;
import com.mrong.lineview.util.TimeUtils;

public class CurrentBreakDownServiceImpl implements ICurrentBreakDownService {

    private ICurrentBreakDownDao currentBreakDownDao;

    public void setCurrentBreakDownDao(ICurrentBreakDownDao currentBreakDownDao) {
        this.currentBreakDownDao = currentBreakDownDao;
    }

    public Map<String, Long> currentFaultInfo(String machineFlg, String startTime) {
        String sql = null;
        Map<String, Object> propertyMap = new HashMap<>();
        switch (machineFlg) {
        case "1":
            sql = "select result_info, begin_time from mechine_selfresult_information where machine_name='洗瓶机' AND ISNULL(end_time) AND begin_time > :startTime order by id desc limit 1;";
            break;
        case "2":
            sql = "select result_info, begin_time from mechine_selfresult_information where machine_name='灌装机' AND ISNULL(end_time) AND begin_time > :startTime order by id desc limit 1;";
            break;
        case "3":
            sql = "select result_info, begin_time from mechine_selfresult_information where machine_name='杀菌机' AND ISNULL(end_time) AND begin_time > :startTime order by id desc limit 1;";
            break;
        case "4":
            sql = "select result_info, begin_time from mechine_selfresult_information where machine_name='贴标机' AND ISNULL(end_time) AND begin_time > :startTime order by id desc limit 1;";
            break;
        default:
            sql = "select result_info, begin_time from mechine_selfresult_information where machine_name='洗瓶机' AND ISNULL(end_time) AND begin_time > :startTime order by id desc limit 1;";
            break;
        }
        propertyMap.put("startTime", TimeUtils.toDate(startTime));
        List<?> list = currentBreakDownDao.getBySql(sql, propertyMap);
        Map<String, Long> result = null;
        String faultName = null;
        Date faultTimes = null;
        result = new HashMap<String, Long>();
        if (list != null && list.size() > 0) {
            Object[] objs = (Object[]) list.get(0);
            faultName = (String) objs[0];
            faultTimes = (Date) objs[1];
            result.put(faultName, faultTimes.getTime());
        } else {
            result.put("未触发", (long) 0);
        }

        return result;
    }
}
