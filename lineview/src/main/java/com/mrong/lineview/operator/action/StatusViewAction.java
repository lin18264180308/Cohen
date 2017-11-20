package com.mrong.lineview.operator.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.ResultDataBean;
import com.mrong.lineview.operator.entity.TimeChooseParameterBean;
import com.mrong.lineview.operator.entity.TimeChooseStatusBean;
import com.mrong.lineview.operator.service.ITimeChooseService;
import com.mrong.lineview.operator.service.ThreeMajorIndicatorsService;
import com.mrong.lineview.operator.service.impl.DataAnalyseAdapter;
import com.mrong.lineview.util.TimeUtils;

public class StatusViewAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = -506122297807395435L;
    private double width = 1.4;

    public void setWidth() {
        double a = TimeUtils.getDisparityOfMinuts(this.getParams().getBegin(), this.getParams().getEnd()) * 60;
        double b = 1000;
        this.width = b / a;
    }

    /**
     * 数据处理的所有结果
     */
    private ResultDataBean resultDataBean;

    private DataAnalyseAdapter dataAnalyseAdapter;

    private ThreeMajorIndicatorsService threeMajorIndicatorsService;

    public void setDataAnalyseAdapter(DataAnalyseAdapter dataAnalyseAdapter) {
        this.dataAnalyseAdapter = dataAnalyseAdapter;
    }

    public void setThreeMajorIndicatorsService(ThreeMajorIndicatorsService threeMajorIndicatorsService) {
        this.threeMajorIndicatorsService = threeMajorIndicatorsService;
    }

    private void initResultDataBean() {
        // 获取处理结果数据
        resultDataBean = dataAnalyseAdapter.getStatus();
        // 更新到队列
        refreshListValue();
    }

    /**
     * 存放机台状态队列（柱状图）
     */
    private List<TimeChooseStatusBean> bottleWashingMachine;
    private List<TimeChooseStatusBean> fillingMachine;
    private List<TimeChooseStatusBean> sterilizationMachine;
    private List<TimeChooseStatusBean> labelingMachine;
    private List<TimeChooseStatusBean> lianDao1;
    private List<TimeChooseStatusBean> lianDao2;
    private List<TimeChooseStatusBean> lianDao3;
    private List<TimeChooseStatusBean> lianDao4;
    private List<String> lianDao1_reason;
    private List<String> lianDao2_reason;
    private List<String> lianDao3_reason;
    private List<String> lianDao4_reason;
    private String lianDao1_color;
    private String lianDao2_color;
    private String lianDao3_color;
    private String lianDao4_color;
    private Double oee;
    private Double gyl;
    private Double lef;

    public Double getOee() {
        return oee;
    }

    public Double getGyl() {
        return gyl;
    }

    public Double getLef() {
        return lef;
    }

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

    public List<String> getLianDao1_reason() {
        return lianDao1_reason;
    }

    public List<String> getLianDao2_reason() {
        return lianDao2_reason;
    }

    public List<String> getLianDao3_reason() {
        return lianDao3_reason;
    }

    public String getLianDao1_color() {
        return lianDao1_color;
    }

    public String getLianDao2_color() {
        return lianDao2_color;
    }

    public String getLianDao3_color() {
        return lianDao3_color;
    }

    public List<String> getLianDao4_reason() {
        return lianDao4_reason;
    }

    public String getLianDao4_color() {
        return lianDao4_color;
    }

    /**
     * 更新队列，将最近一条的处理结果存放到队列
     */
    private void refreshListValue() {
        bottleWashingMachine = offerIntoList(new TimeChooseStatusBean(resultDataBean.getBottleWasher_color(), width),
                bottleWashingMachine);
        fillingMachine = offerIntoList(new TimeChooseStatusBean(resultDataBean.getFillingMachine_color(), width),
                fillingMachine);
        sterilizationMachine = offerIntoList(
                new TimeChooseStatusBean(resultDataBean.getSterilizationMachine_color(), width), sterilizationMachine);
        labelingMachine = offerIntoList(new TimeChooseStatusBean(resultDataBean.getLabelingMachine_color(), width),
                labelingMachine);
        lianDao1 = offerIntoList(new TimeChooseStatusBean(resultDataBean.getLianDao1_color(), width), lianDao1);
        lianDao2 = offerIntoList(new TimeChooseStatusBean(resultDataBean.getLianDao2_color(), width), lianDao2);
        lianDao3 = offerIntoList(new TimeChooseStatusBean(resultDataBean.getLianDao3_color(), width), lianDao3);
        lianDao4 = offerIntoList(new TimeChooseStatusBean(resultDataBean.getLianDao4_color(), width), lianDao4);

        lianDao1_reason = resultDataBean.getLianDao1_reason();
        lianDao2_reason = resultDataBean.getLianDao2_reason();
        lianDao3_reason = resultDataBean.getLianDao3_reason();
        lianDao4_reason = resultDataBean.getLianDao4_reason();

        lianDao1_color = resultDataBean.getLianDao1_color();
        lianDao2_color = resultDataBean.getLianDao2_color();
        lianDao3_color = resultDataBean.getLianDao3_color();
        lianDao4_color = resultDataBean.getLianDao4_color();

        Map<String, Double> map = threeMajorIndicatorsService.indexCalculation(params.getBegin(), params.getEnd());
        this.oee = map.get("oee");
        this.gyl = map.get("gly");
        this.lef = map.get("lef");
    }

    /**
     * 若队列未满，则新增；队列已满，删除第一个元素后再新增
     */
    private List<TimeChooseStatusBean> offerIntoList(TimeChooseStatusBean t, List<TimeChooseStatusBean> list) {
        try {
            double widOfList = addWidthOfList(list);
            TimeChooseStatusBean tmp = null;
            int maxIndex = 0;
            if (list != null && list.size() > 0) {
                removeItem(list); // 删除list中前面所有的宽度为0的元素
            }
            if (list != null && list.size() > 0) {
                if ((widOfList <= this.getParams().getMaxWidth())
                        && ((this.getParams().getMaxWidth() - widOfList) >= width)) {// 当前队列的元素总宽度小于最大宽度，并且与最大宽度的差距大于一个单位宽度，可以继续往后添加元素
                    maxIndex = list.size() - 1;
                    if (list.get(maxIndex).getColor() == t.getColor()) {// 前一个元素颜色与当前颜色一样，则增加前一个的宽度
                        tmp = list.get(maxIndex);
                        tmp.setWidth(tmp.getWidth() + width);
                        list.set(maxIndex, tmp);
                    } else {// 颜色不一样则添加一个新元素
                        list.add(t);
                    }
                } else {// 当前队列的元素总宽度已经接近最大宽度，不能直接添加元素，需要删除或调整第一个元素的宽度
                    tmp = list.get(0);
                    // 判断list中第一个元素宽度，大于等于一个单位则减掉一个单位宽度，不够一个单位则全部删除
                    if (tmp.getWidth() >= width) {
                        tmp.setWidth(tmp.getWidth() - width);// 将第一个元素宽度减掉一个单位
                        list.set(0, tmp);// 替换掉第一个元素
                    } else {// 不足一个单位宽度，删除第一个元素
                        list.remove(0);
                    }
                    maxIndex = list.size() - 1;
                    if (list.get(maxIndex).getColor() == t.getColor()) {// 前一个元素颜色与当前颜色一样，则增加前一个的宽度
                        tmp = list.get(maxIndex);
                        tmp.setWidth(tmp.getWidth() + width);
                        list.set(maxIndex, tmp);
                    } else {
                        list.add(t);
                    }
                }
            } else {
                if (list == null) {
                    list = new ArrayList<>();
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
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
    }

    /**
     * 删除list集合前面宽度属性为0的元素
     * 
     * @param list
     */
    private void removeItem(List<TimeChooseStatusBean> list) {
        TimeChooseStatusBean tmp = list.get(0);
        while (tmp != null && tmp.getWidth() == 0) {
            list.remove(0);
            if (list.size() > 0) {
                tmp = list.get(0);
            } else {
                tmp = null;
            }
        }
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

    private ITimeChooseService timeChooseService;

    public void setTimeChooseService(ITimeChooseService timeChooseService) {
        this.timeChooseService = timeChooseService;
    }

    /**
     * 时间选择范围参数
     */
    /**
     * 时间选择参数
     */
    private String begin;
    private String end;
    private Integer maxWidth;

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setMaxWidth(Integer maxWidth) {
        this.maxWidth = maxWidth;
    }

    private TimeChooseParameterBean params;

    public TimeChooseParameterBean getParams() {
        if (params == null) {
            params = new TimeChooseParameterBean();
        }
        return this.params;
    }

    /**
     * 根据用户选择的时间点至现在的时间范围查询出历史数据，再继续动态往后显示，此方法当确认时间选择时执行一次
     * 路径 : /lineview/getStatusWithDynamicTimeChoose
     * 页面会传时间参数至params
     */
    public String statusWithDynamicTimeChoose() {
        System.err.println("begin : " + begin + "  end : " + end + "  maxWidth : " + maxWidth);
        flg = "FALSE";
        this.getParams().setBegin(TimeUtils.toDate(begin));
        this.getParams().setEnd(TimeUtils.toDate(end));
        this.getParams().setMaxWidth(maxWidth);
        // 根据时间范围计算单位宽度
        this.setWidth();
        flg = "TRUE";
        // 根据时间选择参数，查询某一时间段的历史信息，作为实时状态显示的基础
        handle();

        return SUCCESS;
    }

    /**
     * 时间选择功能触发后，查询一次结果
     */
    private void handle() {
        Map<String, List<TimeChooseStatusBean>> map = timeChooseService.getStatusWithTimeChoose(this.getParams());
        bottleWashingMachine = map.get("bottleWashingMachine");
        fillingMachine = map.get("fillingMachine");
        sterilizationMachine = map.get("sterilizationMachine");
        labelingMachine = map.get("labelingMachine");
        lianDao1 = map.get("lianDao1");
        lianDao2 = map.get("lianDao2");
        lianDao3 = map.get("lianDao3");
        lianDao4 = map.get("lianDao4");

        this.controlWidth();// 统一所有集合宽度
    }

    /**
     * 循环请求，获得json数据渲染至页面
     */
    public String status() {
        initResultDataBean();

        return JSON;
    }

    /**
     * 初始化时间选择参数是否成功的判断标志
     */
    private String flg;

    public String getFlg() {
        return flg;
    }

    /**
     * 初始化时间选择的参数并查询处理，页面刚被打开时执行一次
     */
    public String setParameters() {
        flg = "FALSE";
        Date time = new Date();
        long tmp = time.getTime() - 30 * 60 * 1000;
        this.getParams().setBegin(new Date(tmp));
        this.getParams().setEnd(time);
        this.getParams().setMaxWidth(1000);
        // 根据时间范围计算单位宽度
        this.setWidth();
        flg = "TRUE";
        // 根据时间选择参数，查询某一时间段的历史信息，作为实时状态显示的基础
        handle();

        return SUCCESS;
    }
}
