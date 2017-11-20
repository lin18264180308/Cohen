package com.mrong.lineview.operator.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.Remark;
import com.mrong.lineview.operator.dao.RemarkDao;

/**
 * 备注dao实现类
 * 
 * @author 张裕宝
 */
@SuppressWarnings("all")
public class RemarkDaoImpl extends BaseDaoImpl implements RemarkDao {

	@Override
    public List<Remark> getRemark() {
        String hql = "FROM Remark";
        List<Remark> remarks = getSession().createQuery(hql).list();
        return remarks;
    }

    @Override
    public void saveRemark(Remark r) {
        super.save(r);

    }

    @Override
    public void deleteRemark(Remark r) {
        String hql = "DELETE Remark r WHERE r.id=?";
        getSession().createQuery(hql).setParameter(0, r.getId()).executeUpdate();
    }

    @Override
    public void updateRemark(Remark r, String name, String namevalue) {
        if (name.equals("outageClassification")) {
            String hql = "update Remark m set m.outageClassification=? where m.id=?";
            getSession().createQuery(hql).setParameter(0, namevalue).setParameter(1, r.getId()).executeUpdate();

        }
        if (name.equals("firstClassStopClassification")) {
            String hql = "update Remark m set m.firstClassStopClassification=:name1 where m.id=:mid";
            getSession().createQuery(hql).setParameter("name1", namevalue).setParameter("mid", r.getId())
                    .executeUpdate();

        }
        if (name.equals("secondClassStopClassification")) {
            String hql = "update Remark m set m.secondClassStopClassification=:name1 where m.id=:mid";
            getSession().createQuery(hql).setParameter("name1", namevalue).setParameter("mid", r.getId())
                    .executeUpdate();

        }
        if (name.equals("faultDetails")) {
            String hql = "update Remark m set m.faultDetails=:name1 where m.id=:mid";
            getSession().createQuery(hql).setParameter("name1", namevalue).setParameter("mid", r.getId())
                    .executeUpdate();

        }

    }

    /*
     * 查询总条数(non-Javadoc)
     * @see com.mrong.lineview.operator.dao.RemarkDao#findCount()
     */
    public int findCount() {
        String hql = "SELECT count(*) FROM Remark";
        List<Long> list = getSession().createQuery(hql).list();
        if (list.size() > 0) {
            return list.get(0).intValue();
        }
        return 0;
    }

    /*
     * 根据开始的记录和页数查询数据(non-Javadoc)
     * @see com.mrong.lineview.operator.dao.RemarkDao#findByPage(int, int)
     */
    public List<Remark> findByPage(int begin, int pagesize) {
        String hql = "FROM Remark order by id desc";
        List<Remark> list = getSession().createQuery(hql).setFirstResult(begin).setMaxResults(pagesize).list();
        return list;
    }

}
