package com.mrong.lineview.admin.action;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.common.entity.Entry;

/**
 * 计划停机报表测试类
 * 
 * @author 林金成
 *         2017年11月16日
 */
@SuppressWarnings("all")
public class ScheduledDownTimeActionTest {

    @Test
    public void testLoadData() {
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/applicationContext.xml",
                "applicationContext-test.xml");
        ScheduledDownTimeAction action = (ScheduledDownTimeAction) context.getBean("scheduledDownTimeAction");

        action.setBegin("2017-11-16-16-20");
        action.setEnd("2017-11-16-16-59");
        action.loadData();
        List<Entry<String, Double>> list = action.getScheduledDownTime();
        System.out.println("---------------------------------------------------------");
        for (Entry<String, Double> entry : list) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
        System.out.println("---------------------------------------------------------");
    }

}
