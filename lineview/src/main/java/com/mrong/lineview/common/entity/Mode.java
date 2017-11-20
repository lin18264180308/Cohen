package com.mrong.lineview.common.entity;

/**
 * 模式类
 * 
 * @author 张裕宝
 */
public class Mode {

    public Integer id;
    // 模式名称
    public String modeName;
    // 模式类别
    public String patternClasses;

    public String getPatternClasses() {
        return patternClasses;
    }

    public void setPatternClasses(String patternClasses) {
        this.patternClasses = patternClasses;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }
}
