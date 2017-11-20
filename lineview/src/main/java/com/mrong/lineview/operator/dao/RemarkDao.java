package com.mrong.lineview.operator.dao;

import java.util.List;
import com.mrong.lineview.common.entity.Remark;

/**
 * 获取备注记录
 * 
 * @author 张裕宝
 */

public interface RemarkDao {

    /*
     * 获取所有的备注记录
     */
    public List<Remark> getRemark();

    /*
     * 保存操作
     */
    public void saveRemark(Remark r);

    /*
     * 删除操作
     */
    public void deleteRemark(Remark r);

    /*
     * 修改操作
     */
    public void updateRemark(Remark r, String name, String namevalue);

    /*
     * 查询总条数
     */
    public int findCount();

    /*
     * 分页查询方法
     */
    public List<Remark> findByPage(int begin, int pagesize);
}
