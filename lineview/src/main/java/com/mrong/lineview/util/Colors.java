package com.mrong.lineview.util;

public enum Colors {
    WHITE("rgb(248, 248, 255)", "白色"), RED("rgb(220, 20, 60)", "红色"), YELLOW("rgb(255, 255, 0)", "黄色"), GREEN(
            "rgb(50, 205, 50)", "绿色"), WATHET("rgb(30, 144, 255)", "浅蓝色"), MAZARINE("rgb(0, 0, 139)", "深蓝色");

    /**
     * 顏色名稱
     */
    private String name;
    /**
     * 顏色代码
     */
    private String code;

    private Colors(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
