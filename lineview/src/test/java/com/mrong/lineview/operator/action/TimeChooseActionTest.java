package com.mrong.lineview.operator.action;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.operator.dao.impl.TimeChooseDaoImpl;
import com.mrong.lineview.operator.entity.TimeChooseStatusBean;
import com.mrong.lineview.operator.service.impl.TimeChooseServiceImpl;

@SuppressWarnings("all")
public class TimeChooseActionTest {

    @Test
    public void testGetStatusWithStaticTimeChoose() {
        TimeChooseAction action = new TimeChooseAction();
        TimeChooseServiceImpl service = new TimeChooseServiceImpl();
        TimeChooseDaoImpl dao = new TimeChooseDaoImpl();
        ApplicationContext ac = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
        dao.setSessionFactory(sessionFactory);
        service.setTimeChooseDao(dao);
        action.setTimeChooseService(service);

        action.setBegin("2017-11-16-08-27");//2017-11-15-18-36
        action.setEnd("2017-11-16-08-57");
        action.setMaxWidth(1000);

        action.statusWithStaticTimeChoose();

        System.out.println("洗瓶机 : " + addWidthOfList(action.getBottleWashingMachine()));
        System.out.println("灌装机 : " + addWidthOfList(action.getFillingMachine()));
        System.out.println("杀菌机 : " + addWidthOfList(action.getSterilizationMachine()));
        System.out.println("贴标机 : " + addWidthOfList(action.getLabelingMachine()));
        System.out.println("洗瓶机至EBI链道 : " + addWidthOfList(action.getLianDao1()));
        System.out.println("EBI至灌装机链道 : " + addWidthOfList(action.getLianDao4()));
        System.out.println("灌装机至杀菌机链道 : " + addWidthOfList(action.getLianDao2()));
        System.out.println("杀菌机至贴标机链道 : " + addWidthOfList(action.getLianDao3()));
        List<TimeChooseStatusBean> list = action.getBottleWashingMachine();
        //        List<TimeChooseStatusBean> list = action.getFillingMachine();
        //        List<TimeChooseStatusBean> list = action.getSterilizationMachine();
        //        List<TimeChooseStatusBean> list = action.getLabelingMachine();
        //        List<TimeChooseStatusBean> list = action.getLianDao1();
        //        List<TimeChooseStatusBean> list = action.getLianDao2();
        //        List<TimeChooseStatusBean> list = action.getLianDao3();
        //        List<TimeChooseStatusBean> list = action.getLianDao4();

        for (TimeChooseStatusBean t : list) {
            System.out.println(t.getColor() + " : " + t.getWidth());
        }
    }

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
