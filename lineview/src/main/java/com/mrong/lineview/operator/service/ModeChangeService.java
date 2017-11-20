package com.mrong.lineview.operator.service;

import java.util.List;

import com.mrong.lineview.common.entity.Mode;
import com.mrong.lineview.operator.entity.ModeRecord;

public interface ModeChangeService {
    public List<Mode> getAll();

    public String modeChange(ModeRecord m);

    public List<String> getVarietie();

}
