package com.mrong.lineview.admin.action;

import java.util.List;

import com.mrong.lineview.admin.service.ShiftsService;
import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.Shift;

public class ShiftAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 3259538588494967808L;

    private ShiftsService shiftsService;
    private Shift shift;
    private List<Shift> list;

    public List<Shift> getList() {
        return list;
    }

    public void setList(List<Shift> list) {
        this.list = list;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public void setShiftsService(ShiftsService shiftsService) {
        this.shiftsService = shiftsService;
    }

    /**
     * 增
     * 
     * @return
     */
    public String add() {
        shiftsService.add(shift);
        return SUCCESS;
    }

    /**
     * 删
     * 
     * @return
     */
    public String delete() {
        shiftsService.delete(shift);
        return SUCCESS;
    }

    /**
     * 查
     * 
     * @return
     */
    public String get() {
        list = shiftsService.get();
        return SUCCESS;
    }

    /**
     * 改
     * 
     * @return
     */
    public String edit() {
        shiftsService.edit(shift);
        return SUCCESS;
    }
}
