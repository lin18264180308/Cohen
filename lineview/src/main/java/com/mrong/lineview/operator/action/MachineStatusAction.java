package com.mrong.lineview.operator.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mrong.lineview.common.action.BaseAction;
import com.mrong.lineview.common.entity.ResultDataBean;
import com.mrong.lineview.common.service.OnlineService;
import com.mrong.lineview.operator.entity.TimesOfMachineStop;
import com.mrong.lineview.operator.service.ICurrentSpeedService;
import com.mrong.lineview.operator.service.impl.DataAnalyseAdapter;
import com.mrong.lineview.util.Speed;

/**
 * 机台状态页面请求处理
 * 请求时传递机台标志作为参数，1：洗瓶机；2：灌装机；3：杀菌机；4：贴标机；
 * 
 * @author 林金成
 *         2017年10月26日
 */
@SuppressWarnings("all")
public class MachineStatusAction extends BaseAction {

    /**
     * 
     */
    private static final long serialVersionUID = 6338625868478527094L;

    private DataAnalyseAdapter dataAnalyseAdapter;
    private OnlineService onlineService;
    private ICurrentSpeedService currentSpeedService;

    public void setOnlineService(OnlineService onlineService) {
        this.onlineService = onlineService;
    }

    public void setDataAnalyseAdapter(DataAnalyseAdapter dataAnalyseAdapter) {
        this.dataAnalyseAdapter = dataAnalyseAdapter;
    }

    public void setCurrentSpeedService(ICurrentSpeedService currentSpeedService) {
        this.currentSpeedService = currentSpeedService;
    }

    private ResultDataBean resultDataBean;

    // 机台标志
    private String machineFlg;

    public void setMachineFlg(String machineFlg) {
        this.machineFlg = machineFlg;
    }

    // 大小停机时间和频率信息
    private TimesOfMachineStop stopInfomation;

    // 当前机台的状态
    private int status;

    // 平均速率
    private double averageSpeed;

    // 额定速率
    private double ratedSpeed;

    private double transientSpeed;
    // 故障名
    private List<String> faultName;

    // 故障出现的次数
    private List<Integer> faultTimes;

    // 机台实时状态初始化数据
    private List<Integer> initDatas;

    // faultTimes属性判空处理
    private List<Integer> FillFaultTimes() {
        if (faultTimes != null) {
            return faultTimes;
        } else {
            return new ArrayList<Integer>();
        }

    }

    //faultTimes属性判空处理
    private List<String> FillFaultName() {
        if (faultName != null) {
            return faultName;
        } else {
            return new ArrayList<String>();
        }

    }

    private void loadDataOfBottleWasherMachine() {
        // 获取大停机、小停机信息
        stopInfomation = resultDataBean.getStopInfomationOfBottleWasher();
        // TODO stopInfomation

        // 获取机台各故障点以及出现次数
        Map<String, Integer> map = resultDataBean.getFaultTimesOfBottleWasher();
        // 清空故障信息集合
        faultTimes = FillFaultTimes();
        faultName = FillFaultName();
        faultName.clear();
        faultTimes.clear();
        faultName.addAll(map.keySet());
        for (int i = 0; i < faultName.size(); i++) {
            faultTimes.add(map.get(faultName.get(i)));
        }
        // 获取额定速率和平均速率
        averageSpeed = resultDataBean.getAveSpeedOfBottleWasher();
        ratedSpeed = Speed.BottleWasher.getSpeed();
        initDatas = onlineService.initStatus("1");
    }

    private void loadDataOfFillingMachine() {
        // 获取大停机、小停机信息
        stopInfomation = resultDataBean.getStopInfomationOfFillingMachine();
        // TODO stopInfomation

        // 获取机台各故障点以及出现次数
        Map<String, Integer> map = resultDataBean.getFaultTimesOfFillingMachine();
        // 清空故障信息集合
        faultTimes = FillFaultTimes();
        faultName = FillFaultName();
        faultName.clear();
        faultTimes.clear();
        faultName.addAll(map.keySet());
        for (int i = 0; i < faultName.size(); i++) {
            faultTimes.add(map.get(faultName.get(i)));
        }
        // 获取额定速率和平均速率
        averageSpeed = resultDataBean.getAveSpeedOfFillingMachine();
        ratedSpeed = Speed.FillingMachine.getSpeed();
        initDatas = onlineService.initStatus("2");
    }

    private void loadDataOfSterilizationMachine() {
        // 获取大停机、小停机信息
        stopInfomation = resultDataBean.getStopInfomationOfSterilizationMachine();
        // TODO stopInfomation

        // 获取机台各故障点以及出现次数
        Map<String, Integer> map = resultDataBean.getFaultTimesOfSterilizationMachine();
        // 清空故障信息集合
        faultTimes = FillFaultTimes();
        faultName = FillFaultName();
        faultName.clear();
        faultTimes.clear();
        faultName.addAll(map.keySet());
        for (int i = 0; i < faultName.size(); i++) {
            faultTimes.add(map.get(faultName.get(i)));
        }
        // 获取额定速率和平均速率
        averageSpeed = resultDataBean.getAveSpeedOfSterilizationMachine();
        ratedSpeed = Speed.SterilizationMachine.getSpeed();
        initDatas = onlineService.initStatus("3");
    }

    private void loadDataOfLabelingMachine() {
        // 获取大停机、小停机信息
        stopInfomation = resultDataBean.getStopInfomationOfLabelingMachine();
        // TODO stopInfomation

        // 获取机台各故障点以及出现次数
        Map<String, Integer> map = resultDataBean.getFaultTimesOfLabelingMachine();
        // 清空故障信息集合
        faultTimes = FillFaultTimes();
        faultName = FillFaultName();
        faultName.clear();
        faultTimes.clear();
        faultName.addAll(map.keySet());
        for (int i = 0; i < faultName.size(); i++) {
            faultTimes.add(map.get(faultName.get(i)));
        }
        // 获取额定速率和平均速率
        averageSpeed = resultDataBean.getAveSpeedOfLabelingMachine();
        ratedSpeed = Speed.LabelingMachine.getSpeed();
        initDatas = onlineService.initStatus("4");
    }

    private void loadDataOfMachine() {
        switch (machineFlg) {
        case "1":
            loadDataOfBottleWasherMachine();
            break;
        case "2":
            loadDataOfFillingMachine();
            break;
        case "3":
            loadDataOfSterilizationMachine();
            break;
        case "4":
            loadDataOfLabelingMachine();
            break;
        case "5":
            loadDataOfLabelingMachine();
            break;
        default:
            break;
        }
    }

    public String currentStatus() {
        switch (machineFlg) {
        case "1":
            status = onlineService.currentStatus("1");
            break;
        case "2":
            status = onlineService.currentStatus("2");
            break;
        case "3":
            status = onlineService.currentStatus("3");
            break;
        case "4":
            status = onlineService.currentStatus("4");
            break;
        case "5":
            status = onlineService.currentStatus("4");
            break;
        default:
            status = onlineService.currentStatus("1");
            break;
        }
        return JSON;
    }

    public String initData() {
        initDatas = onlineService.initStatus(machineFlg);
        return JSON;
    }

    public String status() {
        resultDataBean = dataAnalyseAdapter.getStatus();
        // 根据机台标志加载相应的机台数据
        loadDataOfMachine();
        transientSpeed = currentSpeedService.getCurrentSpeed(machineFlg);
        return JSON;
    }

    /**
     * struts2返回JSON数据提供的get方法
     */
    public TimesOfMachineStop getStopInfomation() {
        return stopInfomation;
    }

    public int getStatus() {
        return status;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public double getRatedSpeed() {
        return ratedSpeed;
    }

    public List<String> getFaultName() {
        return faultName;
    }

    public List<Integer> getFaultTimes() {
        return faultTimes;
    }

    public List<Integer> getInitDatas() {
        return initDatas;
    }

    public double getTransientSpeed() {
        return transientSpeed;
    }
}
