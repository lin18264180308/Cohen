package com.mrong.lineview.admin.dao.impl;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.admin.dao.LossOfSpeedDao;
import com.mrong.lineview.util.TimeUtils;

/**
 * 损失报表DAO层测试类
 * 
 * @author 林金成
 *         2017年11月17日
 */
@SuppressWarnings("all")
public class LossOfSpeedDaoImplTest {

    /**
     * 瓶产量测试方法
     */
    @Test
    public void testGetYield() {
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/applicationContext.xml",
                "applicationContext-test.xml");
        LossOfSpeedDao dao = (LossOfSpeedDao) context.getBean("lossOfSpeedDao");

        String begin = "2017-11-04-12-50";
        String end = "2017-11-16-16-25";

        Date startTime = TimeUtils.toDate(begin);
        Date stopTime = TimeUtils.toDate(end);

        int result = dao.getYield(startTime, stopTime);
        System.out.println("result : " + result);
    }
}
