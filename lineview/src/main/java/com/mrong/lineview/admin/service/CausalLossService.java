package com.mrong.lineview.admin.service;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.entity.Entry;

public interface CausalLossService {

    public List<Entry<String, Double>> causalLoss(Date startTime, Date stopTime);
}
