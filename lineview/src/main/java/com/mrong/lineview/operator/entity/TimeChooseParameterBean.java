package com.mrong.lineview.operator.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * 时间选择的参数类
 * 
 * @author 林金成
 *         2017年10月27日
 */
@Component("params")
public class TimeChooseParameterBean {
    private Date begin;// 起始时间
    private Date end;// 结束时间
    private Integer maxWidth;// 最大宽度

    public TimeChooseParameterBean() {
        this.end = new Date();
        this.begin = new Date(this.end.getTime() - 30 * 60 * 1000);
        this.maxWidth = 1000;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public Integer getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }
}
