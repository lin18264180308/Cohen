package com.mrong.lineview.common.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.junit.Test;

import com.mrong.lineview.common.dao.HaltDao;
import com.mrong.lineview.common.entity.HaltBean;
import com.mrong.lineview.common.entity.Online;
import com.mrong.lineview.common.service.HaltService;
import com.mrong.lineview.util.TimeUtils;

@SuppressWarnings("all")
public class HaltServiceImpl implements HaltService {

    // 往后dao层传的机台名称
    private String message;
    // 洗瓶机中间变量
    private boolean bottleWasher;
    // 灌酒机中间变量
    private boolean fillingMachine;
    // 杀菌机中间变量
    private boolean sterilizationMachine;
    // 贴标机中间变量
    private boolean labelingMachine1;
    private HaltDao haltDao;

    public void setHaltDao(HaltDao haltDao) {
        this.haltDao = haltDao;
    }

    HaltBean haltBean = new HaltBean();

    // 定时执行的方法
    class MyTask extends java.util.TimerTask {
        public void run() {

            Online onlines = haltDao.getState();
            System.out.println("________");
            System.out.println(onlines.toString());
            // 洗瓶机
            if (!onlines.isBottleWasher()) {
                bottleWasher = onlines.isBottleWasher();
                System.out.println("boolean" + bottleWasher);
                message = "洗瓶机";
                // 先判断是不是数据库中关于某一机台无数据
                List<HaltBean> haltBeans = haltDao.getALL(message);
                if (haltBeans.size() == 0 || haltBeans == null) {
                    // 是则新插入一条记录
                    haltBean.setStartTime(new Date());
                    haltBean.setShowdownTimers("1");
                    haltBean.setMachineName(message);
                    haltDao.saveMachine(haltBean);
                } else {
                    // 初始化时间计算类
                    // 否先获取上一条记录的开始时间h
                    HaltBean h = haltDao.getTime(message);
                    // 获取历史表中最新的一条记录判断是否要新增或者继续计时
                    List<Object[]> objects = haltDao.getLast();
                    // 如果最新的一条记录是true的话记录
                    if ((boolean) objects.get(0)[0]) {
                        long time = TimeUtils.calculate(new Date(), h.getStartTime());
                        // 然后用当前时间减去开始时间计算出来的值放入到上一条记录中保存
                        if (time > 300) {
                            // 判断是大停机或小停机
                            h.setOutageClassification("大停机");
                        } else {
                            h.setOutageClassification("小停机");
                        }
                        String a = String.valueOf(time);
                        h.setDuration(a);
                        h.setStopTime(new Date());
                        haltDao.updateMachine(h);
                        // 再进行插入下一条操作
                        haltBean.setStartTime(new Date());
                        haltBean.setShowdownTimers("1");
                        haltBean.setMachineName(message);
                        haltDao.saveMachine(haltBean);
                    }
                    // 否则计时
                    else {

                    }
                }

            }
            // 灌酒机
            if (!onlines.isDrinkMachine()) {
                boolean test = onlines.isBottleWasher();
                System.out.println("boolean" + test);
                message = "灌酒机";
                // 先判断是不是数据库中关于某一机台无数据
                List<HaltBean> haltBeans = haltDao.getALL(message);
                if (haltBeans.size() == 0 || haltBeans == null) {
                    // 是则新插入一条记录
                    haltBean.setStartTime(new Date());
                    haltBean.setShowdownTimers("1");
                    haltBean.setMachineName(message);
                    haltDao.saveMachine(haltBean);
                } else {
                    // 初始化时间计算类
                    // 否先获取上一条记录的开始时间h
                    HaltBean h = haltDao.getTime(message);
                    // 获取历史表中最新的一条记录判断是否要新增或者继续计时
                    List<Object[]> objects = haltDao.getLast();
                    // 如果最新的一条记录是true的话记录
                    if ((boolean) objects.get(0)[1]) {
                        long time = TimeUtils.calculate(new Date(), h.getStartTime());
                        // 然后用当前时间减去开始时间计算出来的值放入到上一条记录中保存
                        if (time > 300) {
                            // 判断是大停机或小停机
                            h.setOutageClassification("大停机");
                        } else {
                            h.setOutageClassification("小停机");
                        }
                        String a = String.valueOf(time);
                        h.setDuration(a);
                        h.setStopTime(new Date());
                        haltDao.updateMachine(h);
                        // 再进行插入下一条操作
                        haltBean.setStartTime(new Date());
                        haltBean.setShowdownTimers("1");
                        haltBean.setMachineName(message);
                        haltDao.saveMachine(haltBean);
                    } else {

                    }
                }

            }
            // 杀菌机
            if (!onlines.isSterilizationMachine()) {
                boolean test = onlines.isBottleWasher();
                System.out.println("boolean" + test);
                message = "杀菌机";
                // 先判断是不是数据库中关于某一机台无数据
                List<HaltBean> haltBeans = haltDao.getALL(message);
                if (haltBeans.size() == 0 || haltBeans == null) {
                    // 是则新插入一条记录
                    haltBean.setStartTime(new Date());
                    haltBean.setShowdownTimers("1");
                    haltBean.setMachineName(message);
                    haltDao.saveMachine(haltBean);
                } else {
                    // 初始化时间计算类
                    // 否先获取上一条记录的开始时间h
                    HaltBean h = haltDao.getTime(message);
                    // 获取历史表中最新的一条记录判断是否要新增或者继续计时
                    List<Object[]> objects = haltDao.getLast();
                    // 如果最新的一条记录是true的话记录
                    if ((boolean) objects.get(0)[2]) {
                        long time = TimeUtils.calculate(new Date(), h.getStartTime());
                        // 然后用当前时间减去开始时间计算出来的值放入到上一条记录中保存
                        if (time > 300) {
                            // 判断是大停机或小停机
                            h.setOutageClassification("大停机");
                        } else {
                            h.setOutageClassification("小停机");
                        }
                        String a = String.valueOf(time);
                        h.setDuration(a);
                        h.setStopTime(new Date());
                        haltDao.updateMachine(h);
                        // 再进行插入下一条操作
                        haltBean.setStartTime(new Date());
                        haltBean.setShowdownTimers("1");
                        haltBean.setMachineName(message);
                        haltDao.saveMachine(haltBean);
                    } else {

                    }
                }

            }
            // 贴标机
            if (!onlines.isLabelingMachine1()) {
                boolean test = onlines.isBottleWasher();
                System.out.println("boolean" + test);
                message = "贴标机";
                // 先判断是不是数据库中关于某一机台无数据
                List<HaltBean> haltBeans = haltDao.getALL(message);
                if (haltBeans.size() == 0 || haltBeans == null) {
                    // 是则新插入一条记录
                    haltBean.setStartTime(new Date());
                    haltBean.setShowdownTimers("1");
                    haltBean.setMachineName(message);
                    haltDao.saveMachine(haltBean);
                } else {
                    // 初始化时间计算类
                    // 否先获取上一条记录的开始时间h
                    HaltBean h = haltDao.getTime(message);
                    // 获取历史表中最新的一条记录判断是否要新增或者继续计时
                    List<Object[]> objects = haltDao.getLast();
                    // 如果最新的一条记录是true的话记录
                    if ((boolean) objects.get(0)[3]) {
                        long time = TimeUtils.calculate(new Date(), h.getStartTime());
                        // 然后用当前时间减去开始时间计算出来的值放入到上一条记录中保存
                        if (time > 300) {
                            // 判断是大停机或小停机
                            h.setOutageClassification("大停机");
                        } else {
                            h.setOutageClassification("小停机");
                        }
                        String a = String.valueOf(time);
                        h.setDuration(a);
                        h.setStopTime(new Date());
                        haltDao.updateMachine(h);
                        // 再进行插入下一条操作
                        haltBean.setStartTime(new Date());
                        haltBean.setShowdownTimers("1");
                        haltBean.setMachineName(message);
                        haltDao.saveMachine(haltBean);
                    } else {

                    }
                }

            }
        }
    }

    @Test
    public void haltRecord() {
        // 定时器
        Timer timer = new Timer();
        // 在秒后执行此任务,每次间隔3秒执行一次,如果传递一个Data参数,就可以在某个固定的时间执行这个任务.
        timer.schedule(new MyTask(), 0, 3000);
        while (true) {
            try {
                int in = System.in.read();
                if (in == 's') {
                    // 使用这个方法退出任务
                    timer.cancel();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
