package com.mrong.lineview.operator.action;

import java.util.ArrayList;
import java.util.List;

import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.PageBean;
import com.mrong.lineview.common.entity.Remark;
import com.mrong.lineview.operator.service.RemarkService;

@SuppressWarnings("all")
public class RemarkAction extends BaseAction {

    private Remark remark;
    private List<Remark> remarks;
    private int currPage = 1;
    PageBean<Remark> pageBean;
    private String name;
    private String namevalue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamevalue() {
        return namevalue;
    }

    public void setNamevalue(String namevalue) {
        this.namevalue = namevalue;
    }

    public PageBean<Remark> getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean<Remark> pageBean) {
        this.pageBean = pageBean;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<Remark> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<Remark> remarks) {
        this.remarks = remarks;
    }

    private RemarkService remarkService;

    public Remark getRemark() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public void setRemarkService(RemarkService remarkService) {
        this.remarkService = remarkService;
    }

    /* 保存方法 */
    public String save() {
        remarkService.saveRemark(remark);
        return SUCCESS;
    }

    /* 更新方法 */
    public String update() {
        remarkService.updateRemark(remark, name, namevalue);
        return SUCCESS;

    }

    /* 获取所有备注记录方法 */
    public String get() {
        remarks = new ArrayList<>();
        remarks = remarkService.getRemark();
        return SUCCESS;
    }

    /* 删除 */
    public String delete() {
        remarkService.deleteRemark(remark);
        return SUCCESS;
    }

    /* 分页查询的方法 */
    public String findAll() {
        pageBean = remarkService.findByPage(currPage);
        return SUCCESS;
    }

}
