package com.mrong.lineview.admin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.BreedDao;
import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.Breed;

@SuppressWarnings("all")
public class BreedDaoImpl extends BaseDaoImpl implements BreedDao {

    /**
     * 添加生产品种
     */
    public void add(Breed b) {
        super.save(b);
    }

    /**
     * 删除生产品种
     */
    public void delete(Breed b) {
        String sql = "DELETE FROM varieties WHERE id =:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", b.getId());
        this.executeBySql(sql, map);
    }

    /**
     * 获取所有品种
     */
    public List<Breed> getAll() {
        String hql = "FROM Bread";
        List<Breed> list = (List<Breed>) this.getByHql(hql, null);
        if (list == null || list.size() == 0) {
            list = null;
        }

        return list;
    }

    /**
     * 编辑生产品种
     */
    public void edit(Breed b) {
        super.update(b);
    }
}
