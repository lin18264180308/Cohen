package com.mrong.lineview.operator.dao.impl;

import org.springframework.stereotype.Repository;

import com.mrong.lineview.common.dao.impl.BaseDaoImpl;
import com.mrong.lineview.operator.dao.ITimeChooseDao;
import com.mrong.lineview.operator.entity.MechineStopInformationBean;
@SuppressWarnings("all")
public class TimeChooseDaoImpl extends BaseDaoImpl implements ITimeChooseDao {
    public void save(MechineStopInformationBean m) {
        super.save(m);
    }
}
