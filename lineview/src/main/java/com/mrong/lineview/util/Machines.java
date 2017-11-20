package com.mrong.lineview.util;

/**
 * 机台名称枚举类
 * 
 * @author 林金成
 *         2017年10月18日
 */
public enum Machines {

    FILLINGMACHINE("灌装机"), LABELINGMACHINE("贴标机"), STERILIZATIONMACHINE("杀菌机"), BOTTLEWASHINGMACHINE("洗瓶机"), LIANDAO1(
            "洗瓶机至灌装机链道"), LIANDAO2("灌装机至杀菌机链道"), LIANDAO3("杀菌机至贴标机链道"), LIANDAO4("EBI至灌装机链道");

    private String name;

    public String getName() {
        return name;
    }

    private Machines(String name) {
        this.name = name;
    }
}
