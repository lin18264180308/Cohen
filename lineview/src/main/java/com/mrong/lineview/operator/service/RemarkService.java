package com.mrong.lineview.operator.service;

import java.util.List;

import com.mrong.lineview.common.entity.PageBean;
import com.mrong.lineview.common.entity.Remark;

public interface RemarkService {

    /*
     * 获取所有备注
     */
    public List<Remark> getRemark();

    /*
     * 删除备注
     */
    public void deleteRemark(Remark r);

    /*
     * 更新备注
     */

    public void updateRemark(Remark r, String name, String namevalue);

    /*
     * 保存备注
     */
    public void saveRemark(Remark r);

    /*
     * 通过页数返回备注
     */
    public PageBean<Remark> findByPage(Integer currPage);
}
