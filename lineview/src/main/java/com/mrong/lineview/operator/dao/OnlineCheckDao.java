package com.mrong.lineview.operator.dao;

import com.mrong.lineview.operator.entity.OnlineCheck;

public interface OnlineCheckDao {

    public void saveOnlineCheck(OnlineCheck o);

    public Object[] getStaus();
}
