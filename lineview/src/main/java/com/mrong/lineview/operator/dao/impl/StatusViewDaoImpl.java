package com.mrong.lineview.operator.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.operator.dao.IStatusViewDao;
import com.mrong.lineview.operator.entity.MechineStopInformationBean;
import com.mrong.lineview.util.Colors;

/**
 * 数据采集处理Dao层
 * 
 * @author 林金成
 *         2017年11月2日
 */
@SuppressWarnings("all")
public class StatusViewDaoImpl extends BaseDaoImpl implements IStatusViewDao {

    /**
     * 获取一个机台平均速度
     * 
     * @param list
     *            : 问张裕宝
     * @param duration
     *            : 当前班次工作的持续时间（分钟）
     * @return
     */
    public int getAvgSpeedOfMachine(List<?> list, int duration, double xishu) {
        double result = 0;
        int count = 0;
        if (list != null) {
            for (Object d : list) {
                result += (double) d;
                count++;
            }
        }

        int fenzi = (int) (result * xishu);

        return (count != 0 ? ((int) fenzi / count) : 0);
    }

    /**
     * 统计单机台的停机次数
     */
    public Map<String, Integer> getStopTimesOfMachine(List<String> keys, List<?> values) {
        Map<String, Integer> result = new HashMap<>();
        if (keys != null && values != null) {
            for (int i = 0; i < values.size(); i++) {
                result.put(keys.get(i), (Integer) values.get(i));
            }
        }

        return result;
    }

    /**
     * 获取数据库中当前班次的所有信息
     * 
     * @param sql
     *            : sql语句
     * @param propertyMap
     *            : 参数集合
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<MechineStopInformationBean> getStopInfomationOfMachine(String sql, Map<String, Object> propertyMap) {
        List<MechineStopInformationBean> result = new ArrayList<>();
        List<Object[]> list = (List<Object[]>) this.getBySql(sql, propertyMap);
        for (Object[] objs : list) {
            result.add(new MechineStopInformationBean(null, (String) objs[0], (Date) objs[1],
                    ((Date) objs[2] == null ? new Date() : (Date) objs[2]), (String) objs[3]));
        }

        return result;
    }

    /**
     * 获取当前班次开始工作的时间
     * 
     * @return
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    public Date getStartTime() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minutes = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        String hourStr = String.valueOf(hour);
        String minutesStr = String.valueOf(minutes);
        String secondStr = String.valueOf(second);
        if (hour < 10) {
            hourStr = "0" + hourStr;
        }
        if (minutes < 10) {
            minutesStr = "0" + minutesStr;
        }
        if (second < 10) {
            secondStr = "0" + secondStr;
        }
        String nowStr = "" + hourStr + ":" + minutesStr + ":" + secondStr + "";
        String sql = "select start_time from shift where start_time <= '" + nowStr + "' and stop_time > '" + nowStr
                + "';";

        List<?> list = (List<Object[]>) this.getBySql(sql, null);
        String[] str = list.get(0).toString().split(":");
        hour = Integer.parseInt(str[0]);
        minutes = Integer.parseInt(str[1]);
        second = Integer.parseInt(str[2]);
        // 当前班次的开始时间
        Date begin = new Date();//(Date) objs[0];
        begin.setHours(hour);
        begin.setMinutes(minutes);
        begin.setSeconds(second);
        return begin;
    }

    /**
     * 获取机台停机时间
     */
    public Date getMachineStopTime(String sql, Map<String, Object> propertyMap) {
        Date result = null;
        List<Object[]> objs = (List<Object[]>) this.getBySql(sql, propertyMap);
        if (objs.size() > 0) {
            Object[] obj = objs.get(0);
            String color = (String) obj[1];
            // 当颜色为红色、浅蓝色、深蓝色时，为停机状态
            if (color.equals(Colors.RED.getName()) || color.equals(Colors.MAZARINE.getName())
                    || color.equals(Colors.WATHET.getName())) {
                result = (Date) obj[0];
            } else {
                result = new Date();
                result.setYear(result.getYear() + 10);
            }
        }

        return result;
    }

    /**
     * 查询机台的故障信号，跟key匹配
     */
    public Map<String, Integer> getPointsOfMachine(String sql, List<String> keys) {
        List<Object[]> list = (List<Object[]>) this.getBySql(sql, null);
        Map<String, Integer> map = null;
        if (list.size() > 0) {
            Object[] objs = list.get(0);
            int len = objs.length - 1;
            map = new HashMap<>();
            for (int i = 0; i <= len; i++) {
                if (i != 0 && i != len) {
                    if ((Boolean) objs[i]) {
                        map.put(keys.get(i - 1), new Integer(1));
                    } else {
                        map.put(keys.get(i - 1), new Integer(0));
                    }
                }
            }
        }

        return map;
    }
}
