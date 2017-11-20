package com.mrong.lineview.admin.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.dao.ShiftDao;
import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.common.entity.Shift;

@SuppressWarnings("all")
public class ShiftDaoImpl extends BaseDaoImpl implements ShiftDao {

    @Override
    public void addShift(Shift s) {
        super.save(s);

    }

    @Override
    public void deleteShift(int id) {
        String sql = "DELETE FROM shift WHERE id=:id";
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        this.executeBySql(sql, map);
    }

    @Override
    public List<Shift> getShift() {
        String hql = "FROM Shift";
        List<Shift> shifts = (List<Shift>) this.getByHql(hql, null);
        if (shifts == null || shifts.size() == 0) {
            shifts = null;
        }
        return shifts;
    }

    @Override
    public void editShift(Shift s) {
        super.update(s);
    }
}
