package com.mrong.lineview.operator.action;

import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.operator.entity.TimeChooseParameterBean;
import com.mrong.lineview.operator.entity.TimeChooseStatusBean;
import com.mrong.lineview.operator.service.ITimeChooseService;
import com.mrong.lineview.util.TimeUtils;

public class TimeChooseAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4625360611814016023L;

    private ITimeChooseService timeChooseService;

    public void setTimeChooseService(ITimeChooseService timeChooseService) {
        this.timeChooseService = timeChooseService;
    }

    private List<TimeChooseStatusBean> bottleWashingMachine;
    private List<TimeChooseStatusBean> fillingMachine;
    private List<TimeChooseStatusBean> sterilizationMachine;
    private List<TimeChooseStatusBean> labelingMachine;
    private List<TimeChooseStatusBean> lianDao1;
    private List<TimeChooseStatusBean> lianDao2;
    private List<TimeChooseStatusBean> lianDao3;
    private List<TimeChooseStatusBean> lianDao4;

    public List<TimeChooseStatusBean> getBottleWashingMachine() {
        return bottleWashingMachine;
    }

    public List<TimeChooseStatusBean> getFillingMachine() {
        return fillingMachine;
    }

    public List<TimeChooseStatusBean> getSterilizationMachine() {
        return sterilizationMachine;
    }

    public List<TimeChooseStatusBean> getLabelingMachine() {
        return labelingMachine;
    }

    public List<TimeChooseStatusBean> getLianDao1() {
        return lianDao1;
    }

    public List<TimeChooseStatusBean> getLianDao2() {
        return lianDao2;
    }

    public List<TimeChooseStatusBean> getLianDao3() {
        return lianDao3;
    }

    public List<TimeChooseStatusBean> getLianDao4() {
        return lianDao4;
    }

    /**
     * 时间选择参数
     */
    private String begin;
    private String end;
    private Integer maxWidth;

    private TimeChooseParameterBean params;

    public TimeChooseParameterBean getParams() {
        if (params == null) {
            params = new TimeChooseParameterBean();
        }
        return params;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    // 路径------>/lineview/getStatusWithStaticTimeChoose
    public String statusWithStaticTimeChoose() {
        this.getParams().setBegin(TimeUtils.toDate(begin));
        this.getParams().setEnd(TimeUtils.toDate(end));
        this.getParams().setMaxWidth(maxWidth);
        Map<String, List<TimeChooseStatusBean>> result = timeChooseService.getStatusWithTimeChoose(params);
        bottleWashingMachine = result.get("bottleWashingMachine");
        fillingMachine = result.get("fillingMachine");
        sterilizationMachine = result.get("sterilizationMachine");
        labelingMachine = result.get("labelingMachine");
        lianDao1 = result.get("lianDao1");
        lianDao2 = result.get("lianDao2");
        lianDao3 = result.get("lianDao3");
        lianDao4 = result.get("lianDao4");

        controlWidth();

        return JSON;
    }

    /**
     * 控制所有的集合宽度
     */
    public void controlWidth() {
        double baseWidth = 1000;

        controlWidthOfMachine(baseWidth, bottleWashingMachine);
        controlWidthOfMachine(baseWidth, fillingMachine);
        controlWidthOfMachine(baseWidth, sterilizationMachine);
        controlWidthOfMachine(baseWidth, labelingMachine);
        controlWidthOfMachine(baseWidth, lianDao1);
        controlWidthOfMachine(baseWidth, lianDao2);
        controlWidthOfMachine(baseWidth, lianDao3);
        controlWidthOfMachine(baseWidth, lianDao4);
    }

    private void controlWidthOfMachine(Double baseWidth, List<TimeChooseStatusBean> list) {
        double tmpWidth = addWidthOfList(list);// 机台总宽度
        TimeChooseStatusBean tmp = null;
        double difference = 0.0;// 宽度差距
        int index = list.size() - 1;// 最后一个元素的下标

        while (tmpWidth > baseWidth) {// 针对宽度大于1000的情况
            difference = tmpWidth - baseWidth;// 差值
            tmp = list.get(index);
            if (tmp.getWidth() > difference) {// 最后一个元素的宽度大于差值，直接减掉
                tmp.setWidth(tmp.getWidth() - difference);
                list.set(index, tmp);// 将最后一个元素替换成修改过宽度的
                tmpWidth = addWidthOfList(list);// 从新计算宽度
            } else {// 最后一个元素的宽度小于等于差值，先删掉最后一个元素
                list.remove(index);
                index = list.size() - 1;// 将 index设为最后一个元素的下标
                tmpWidth = addWidthOfList(list);// 从新计算宽度
            }
        }

        /* while (tmpWidth < baseWidth) {// 针对宽度小于1000的情况
         * difference = baseWidth - tmpWidth;// 差值
         * tmp = list.get(index);
         * tmp.setWidth(tmp.getWidth() + difference);
         * list.set(index, tmp);
         * } */
    }

    /**
     * 计算list集合的所有元素的width属性和
     * 
     * @param list
     * @return
     */
    private double addWidthOfList(List<TimeChooseStatusBean> list) {
        double result = 0;
        if (list != null && list.size() > 0) {
            for (TimeChooseStatusBean t : list) {
                result += t.getWidth();
            }
        }

        return result;
    }
}
