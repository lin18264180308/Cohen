package com.mrong.lineview.admin.action;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.common.entity.Entry;

/**
 * 速度损失报表测试类
 * 
 * @author 林金成
 *         2017年11月16日
 */
@SuppressWarnings("all")
public class SpeedLossAreaActionTest {

    @Test
    public void testLoadData() {
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/applicationContext.xml",
                "applicationContext-test.xml");
        SpeedLossAreaAction action = (SpeedLossAreaAction) context.getBean("speedLossAreaAction");

        action.setBegin("2017-11-16-14-08");
        action.setEnd("2017-11-16-14-38");
        action.loadData();

        List<Entry<String, Double>> list = action.getSpeedArea();
        for (Entry<String, Double> entry : list) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}
