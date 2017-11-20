package com.mrong.lineview.common.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import com.mrong.lineview.common.dao.IBaseDao;

/**
 * @author 林金成
 *         2017年10月18日
 */
public class BaseDaoImpl implements IBaseDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session;

    public Session getSession() {
        if (session == null || !session.isOpen()) {
            try {
                session = sessionFactory.getCurrentSession();
            } catch (HibernateException e) {
                session = sessionFactory.openSession();
            }
        }
        return session;
    }

    @Override
    public void save(Object obj) {
        getSession().save(obj);
    }

    @Override
    public void delete(Class<?> objClass, Serializable id) {
        getSession().delete(getSession().get(objClass, id));
    }

    @Override
    public void update(Object obj) {
        getSession().update(obj);
    }

    @Override
    public Object get(Class<?> objClass, Serializable id) {
        return getSession().get(objClass, id);
    }

    @Override
    public List<?> getByHql(String hql, Map<String, Object> propertyMap) {
        Query<?> query = getSession().createQuery(hql);
        for (String key : propertyMap.keySet()) {
            query.setParameter(key, propertyMap.get(key));
        }

        return query.list();
    }

    @Override
    public List<?> getBySql(String sql, Map<String, Object> propertyMap) {
        NativeQuery<?> query = getSession().createNativeQuery(sql);
        if (propertyMap != null) {
            for (String key : propertyMap.keySet()) {
                query.setParameter(key, propertyMap.get(key));
            }
        }
        return query.list();
    }

    @Override
    public int executeBySql(String sql, Map<String, Object> propertyMap) {
        NativeQuery<?> query = getSession().createNativeQuery(sql);
        if (propertyMap != null) {
            for (String key : propertyMap.keySet()) {
                query.setParameter(key, propertyMap.get(key));
            }
        }

        return query.executeUpdate();
    }
}
