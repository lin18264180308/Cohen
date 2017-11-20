package com.mrong.lineview.operator.service.impl;

import java.util.List;

import com.mrong.lineview.common.entity.PageBean;
import com.mrong.lineview.common.entity.Remark;
import com.mrong.lineview.operator.dao.RemarkDao;
import com.mrong.lineview.operator.service.RemarkService;

public class RemarkServiceImpl implements RemarkService {

    private RemarkDao remarkDao;

    public void setRemarkDao(RemarkDao remarkDao) {
        this.remarkDao = remarkDao;
    }

    @Override
    public List<Remark> getRemark() {
        List<Remark> list = remarkDao.getRemark();
        return list;
    }

    @Override
    public void deleteRemark(Remark r) {
        remarkDao.deleteRemark(r);

    }

    @Override
    public void updateRemark(Remark r, String name, String namevalue) {
        remarkDao.updateRemark(r, name, namevalue);

    }

    @Override
    public void saveRemark(Remark r) {
        remarkDao.saveRemark(r);

    }

    @Override
    public PageBean<Remark> findByPage(Integer currPage) {
        PageBean<Remark> pageBean = new PageBean<Remark>();
        // 封装当前页数
        pageBean.setCurrPage(currPage);
        // 定义每页显示的记录条数
        int pageSize = 8;
        pageBean.setPageSize(pageSize);
        // 装总记录数
        int totalCount = remarkDao.findCount();
        pageBean.setTotalCount(totalCount);
        // 封装总页数
        double tc = totalCount;
        // 向上取整
        double num = Math.ceil(tc / pageSize);
        pageBean.setTotalPage((int) num);
        // 封装每页显示的数据
        int begin = (currPage - 1) * pageSize;
        List<Remark> list = remarkDao.findByPage(begin, pageSize);
        // 把list装入pagebean的list属性中
        pageBean.setList(list);
        return pageBean;
    }

}
