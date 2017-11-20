package com.mrong.lineview.admin.action;

import java.util.List;
import java.util.Map;

import com.mrong.lineview.admin.service.ProductionReportService;
import com.mrong.lineview.operator.service.ThreeMajorIndicatorsService;
import com.mrong.lineview.util.TimeUtils;

/**
 * 生产线绩效 生产线绩效内容包括产量、OEE、GLY、LEF
 * 
 * @author : 张裕宝
 */
public class ProductionReportAction {

    // 前端给的开始时间，结束时间
    private String startTime;
    private String stopTime;
    // 返回给前端的数据
    private List<Double> list;
    // 获得酒机瓶数
    private ProductionReportService productionReportService;
    // 获得三大指标（oee等）
    private ThreeMajorIndicatorsService threeMajorIndicatorsService;

    public void setThreeMajorIndicatorsService(ThreeMajorIndicatorsService threeMajorIndicatorsService) {
        this.threeMajorIndicatorsService = threeMajorIndicatorsService;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public void setProductionReportService(ProductionReportService productionReportService) {
        this.productionReportService = productionReportService;
    }

    public List<Double> getList() {
        return list;
    }

    public void setList(List<Double> list) {
        this.list = list;
    }

    // 返回生产绩效的四个指标
    public String productionReport() {
        double count = productionReportService.getFillingMachineCount(TimeUtils.toDate(startTime),
                TimeUtils.toDate(stopTime));
        Map<String, Double> map = threeMajorIndicatorsService.indexCalculation(TimeUtils.toDate(startTime),
                TimeUtils.toDate(stopTime));

        list.add(count);
        list.add(map.get("oee"));
        list.add(map.get("gly"));
        list.add(map.get("lef"));

        return "success";
    }
}
