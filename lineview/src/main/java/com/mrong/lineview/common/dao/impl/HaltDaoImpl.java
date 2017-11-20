package com.mrong.lineview.common.dao.impl;

import java.util.Date;
import java.util.List;
import com.mrong.lineview.common.dao.HaltDao;
import com.mrong.lineview.common.entity.HaltBean;
import com.mrong.lineview.common.entity.Online;
@SuppressWarnings("all")
public class HaltDaoImpl extends BaseDaoImpl implements HaltDao {

    @Override
    public List<HaltBean> getMachine(String message) {
        String hql = "FROM HaltBean h WHERE h.machineName=?";
        List<HaltBean> haltBeans = getSession().createQuery(hql).setParameter(0, message).list();
        return haltBeans;
    }

    @Override
    public void saveMachine(HaltBean h) {
        getSession().save(h);

    }

    @Override
    public HaltBean getTime(String message) {
        HaltBean temp = new HaltBean();
        String sql = "SELECT * FROM haltbean where  id=(select max(id)  from ( select id from haltbean where machine_Name=?) as temp)";
        List<Object[]> haltBeans = getSession().createSQLQuery(sql).setParameter(0, message).list();
        temp.setId((Integer) haltBeans.get(0)[0]);
        temp.setMachineName((String) haltBeans.get(0)[1]);
        temp.setShowdownTimers((String) haltBeans.get(0)[3]);
        temp.setStartTime((Date) haltBeans.get(0)[4]);
        System.out.println(temp.getId() + "   " + temp.getMachineName() + "   " + temp.getShowdownTimers() + "   "
                + temp.getStartTime());
        return temp;
    }

    @Override
    public List<HaltBean> getALL(String message) {
        String hql = "FROM HaltBean h WHERE h.machineName=?";
        List<HaltBean> haltBeans = getSession().createQuery(hql).setParameter(0, message).list();
        return haltBeans;
    }

    @Override
    public void updateMachine(HaltBean h) {
        getSession().update(h);
    }

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

    @Override
    public List<Object[]> getLast() {
        String sql = "SELECT BOTTLE_WASHER,DRINK_MACHINE,STERILIZATION_MACHINE,LABELING_MACHINE1,LABELING_MACHINE2 FROM online_history WHERE ID=(SELECT MAX(ID) FROM online_history )";
        List<Object[]> objects = getSession().createSQLQuery(sql).list();
        return objects;
    }

}
