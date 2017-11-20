package com.mrong.lineview.common.action;

import com.mrong.lineview.common.service.HaltService;

/**
 * 四大机台停机分类相关的计算
 * 
 * @author 张裕宝
 */
@SuppressWarnings("all")
public class HaltAction extends BaseAction {

    private HaltService haltService;
    // 接收前台传过来的机台名字
    private String flag;

    public void setHaltService(HaltService haltService) {
        this.haltService = haltService;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * 根据传递过来的flag判断检索那张数据库中的表
     * 
     * @return
     */
    public String halt() {
        if (flag.equals("洗瓶机")) {

        }
        if (flag.equals("灌酒机")) {

        }
        if (flag.equals("杀菌机")) {

        }
        if (flag.equals("贴标机")) {

        }

        return "json";
    }

    public void haltRecord() {
        // 执行定时方法
        System.out.println("已经执行定时方法");
        haltService.haltRecord();
    }

}
