package com.mrong.lineview.operator.entity;

/**
 * 故障信息类
 * 
 * @author 张裕宝
 */
public class FaultMessage {

    private Integer id;
    // 故障名称
    private String faultName;
    // 故障机台
    private String faultMachine;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFaultName() {
        return faultName;
    }

    public void setFaultName(String faultName) {
        this.faultName = faultName;
    }

    public String getFaultMachine() {
        return faultMachine;
    }

    public void setFaultMachine(String faultMachine) {
        this.faultMachine = faultMachine;
    }

}
