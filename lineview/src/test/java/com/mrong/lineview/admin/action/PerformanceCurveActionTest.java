package com.mrong.lineview.admin.action;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.admin.entity.PerformanceIndicators;

/**
 * 绩效曲线报表测试类
 * 
 * @author 林金成
 *         2017年11月17日
 */
@SuppressWarnings("all")
public class PerformanceCurveActionTest {

    @Test
    public void testLoadData() {
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/applicationContext.xml",
                "applicationContext-test.xml");
        PerformanceCurveAction action = (PerformanceCurveAction) context.getBean("performanceCurveAction");

        action.setBegin("2017-11-01-14-08-00");
        action.setEnd("2017-11-15-14-38-00");
        action.loadData();

        PerformanceIndicators performanceCurve = action.getPerformanceCurve();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>performanceCurve<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("oee : " + performanceCurve.getOee());
        System.out.println("lef : " + performanceCurve.getLef());
        System.out.println("gly : " + performanceCurve.getGly());
        System.out.println("yield : " + performanceCurve.getYield());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>performanceCurve<<<<<<<<<<<<<<<<<<<<<");

        List<PerformanceIndicators> performanceCurveOfDate = action.getPerformanceCurveOfDate();

        System.out.println(">>>>>>>>>>>>>>>>>>>>>performanceCurveOfDate<<<<<<<<<<<<<<<<<<<<<");
        for (PerformanceIndicators p : performanceCurveOfDate) {
            System.out.println("oee : " + p.getOee());
            System.out.println("lef : " + p.getLef());
            System.out.println("gly : " + p.getGly());
            System.out.println("yield : " + p.getYield());
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>performanceCurveOfDate<<<<<<<<<<<<<<<<<<<<<");
    }
}
