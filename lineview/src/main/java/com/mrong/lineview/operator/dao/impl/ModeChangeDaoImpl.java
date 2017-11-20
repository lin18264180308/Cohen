package com.mrong.lineview.operator.dao.impl;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.Mode;
import com.mrong.lineview.operator.dao.ModeChangeDao;
import com.mrong.lineview.operator.entity.ModeRecord;

@SuppressWarnings("all")
public class ModeChangeDaoImpl extends BaseDaoImpl implements ModeChangeDao {

    /* 获取数据库中所有的模式(non-Javadoc)
     * @see com.mrong.lineview.operator.dao.ModeChangeDao#get() */
    public List<Mode> get() {
        String hql = "FROM Mode";
        List<Mode> mode = getSession().createQuery(hql).list();
        return mode;
    }

    /* 切换模式 ，保存持续时间
     * @see
     * com.mrong.lineview.operator.dao.ModeChangeDao#updateTime(com.mrong.lineview.
     * operator.entity.ModeRecord) */
    public void updateTime(ModeRecord m) {
        super.update(m);
    }

    /* 获取上一条记录的开始时间
     * @see com.mrong.lineview.operator.dao.ModeChangeDao#getTime() */
    public ModeRecord getTime() {
        ModeRecord temp = new ModeRecord();
        String hql = "SELECT m.id,m.modeClass,m.modeName,m.operator,m.startTime,m.patternClasses FROM ModeRecord m WHERE m.id=(SELECT MAX(mm.id) FROM ModeRecord mm)";
        List<Object[]> query = getSession().createQuery(hql).list();
        temp.setId((Integer) query.get(0)[0]);
        temp.setModeClass((String) query.get(0)[1]);
        temp.setModeName((String) query.get(0)[2]);
        temp.setOperator((String) query.get(0)[3]);
        temp.setStartTime((Date) query.get(0)[4]);
        temp.setPatternClasses((String) query.get(0)[5]);

        System.out.println(temp.toString());
        return temp;
    }

    /* 保存最新一条记录（不包含持续时间）
     * @see com.mrong.lineview.operator.dao.ModeChangeDao#saveModeChange(com.mrong.
     * lineview.operator.entity.ModeRecord) */
    public void saveModeChange(ModeRecord modeRecord) {
        System.err.println("back:" + modeRecord);
        super.save(modeRecord);

    }

}
