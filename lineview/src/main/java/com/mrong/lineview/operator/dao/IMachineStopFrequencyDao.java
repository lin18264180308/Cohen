package com.mrong.lineview.operator.dao;

import java.util.Date;

import com.mrong.lineview.common.dao.IBaseDao;

public interface IMachineStopFrequencyDao extends IBaseDao {
    public double getMachineStopFrequency(Date beginTime, Date endTime, String machineName, String color);

    public boolean getMachineFaultDuration(String resultInfo, Date beginTime, Date endTime);
}
