package com.mrong.lineview.common.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.mrong.lineview.common.dao.OnlineDao;
import com.mrong.lineview.common.entity.Online;

/**
 * 整线一览实现类
 * 
 * @author 张裕宝
 */
@SuppressWarnings("all")
public class OnlineDaoImpl extends BaseDaoImpl implements OnlineDao {

    /**
     * 实现方法
     */
    public Online getState() {
        Online online = new Online();
        String hql = "FROM Online";
        List<Online> list = getSession().createQuery(hql).list();
        if (list == null || list.size() == 0) {
            online = null;
        } else {
            online = list.get(0);
        }
        return online;
    }
    /**
     * 实现方法
     */
	@Override
	public List<Integer> initStatus(String halfSql){
		List<Integer> initDatas = null;
		/******日期转换********/
		//SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " ); 
		Date start = new Date(new Date().getTime()-120*1000);
		Date end = new Date();
		try {
			List<?> list = null;
			Map<String, Object> propertyMap = new HashMap<>();
			propertyMap.put("start", start);
			propertyMap.put("end", end);
			list = this.getBySql(halfSql, propertyMap);
			if (list == null || list.size() == 0) {
				initDatas = null;
			} else {
				initDatas = new ArrayList<Integer>();
				for (int i = 0; i < list.size(); i++) {
					//System.out.println(list.get(i)[1]);
						if((Boolean)list.get(i)){
							initDatas.add(1);
						}else{
							initDatas.add(0);
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		System.err.println("------------DAO-----------"+initDatas+"---------------------------------");
		return initDatas;
		/**************/
	}

	@Override
	public Integer currentStatus(String sql) {
		List<?> result = this.getBySql(sql, null);
		return (Boolean) result.get(0)? 1 : 0;
	}

}
