package com.mrong.lineview.operator.entity;

/**
 * 时间选择功能执行后，检索时间间隔的，保存颜色和宽度
 * 
 * @author 林金成
 *         2017年10月18日
 */
public class TimeChooseStatusBean {

    private String color;
    private double width;

    public TimeChooseStatusBean() {
    }

    public TimeChooseStatusBean(String color, double width) {
        this.color = color;
        this.width = width;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
