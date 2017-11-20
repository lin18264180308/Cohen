package com.mrong.lineview.operator.action;

import java.util.Date;
import java.util.List;

import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Mode;
import com.mrong.lineview.operator.entity.ModeRecord;
import com.mrong.lineview.operator.service.ModeChangeService;

public class ModeChangeAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 9104378143515272504L;

    private ModeChangeService modeChangeService;
    // 模式记录
    private ModeRecord modeRecord;
    // 返回给前台的所有模式
    private List<Mode> modes;
    // 返回给前台的所有品种
    private List<String> varieties;

    public ModeRecord getModeRecord() {
        return modeRecord;
    }

    public void setModeRecord(ModeRecord modeRecord) {
        this.modeRecord = modeRecord;
    }

    public List<Mode> getModes() {
        return modes;
    }

    public List<String> getVarieties() {
        return varieties;
    }

    public void setModeChangeService(ModeChangeService modeChangeService) {
        this.modeChangeService = modeChangeService;
    }

    /* 获得所有的模式 */
    public String getAll() {
        modes = modeChangeService.getAll();
        return "success";
    }

    /* 获得所有的模式 */
    public String getVarietie() {
        varieties = modeChangeService.getVarietie();
        return "success";
    }

    /* 切换模式操作 */
    public String modeChange() {
        /* System.out.println("操作工" + modeRecord.getOperator() + "模式名" +
         * modeRecord.getModeName() + "模式类别" + modeRecord.getModeClass()); */
        modeRecord.setStartTime(new Date());
        modeChangeService.modeChange(modeRecord);
        // 将模式名称放进application
        application.put("modeName", modeRecord.getModeName());
        // 将品种放入application
        application.put("modeClass", modeRecord.getModeClass());
        System.out.println(">>>>>>>>>>>>>>" + application.get("modeName"));
        System.out.println(">>>>>>>>>>>>>>" + application.get("modeClass"));
        return "success";
    }
}
