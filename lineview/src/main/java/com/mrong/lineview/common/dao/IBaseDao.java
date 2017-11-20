package com.mrong.lineview.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 林金成
 *         2017年10月18日
 */
public interface IBaseDao {

    public void save(Object obj);

    public void delete(Class<?> objClass, Serializable id);

    public void update(Object obj);

    public Object get(Class<?> objClass, Serializable id);

    public List<?> getByHql(String hql, Map<String, Object> propertyMap);

    public List<?> getBySql(String sql, Map<String, Object> propertyMap);

    int executeBySql(String sql, Map<String, Object> propertyMap);
}
