package com.mrong.lineview.admin.action;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.admin.dao.impl.LossOfSpeedDaoImpl;
import com.mrong.lineview.admin.service.impl.ProductionReportServiceImpl;

/**
 * 生产线指标报表测试类
 * 
 * @author 林金成
 *         2017年11月16日
 */
@SuppressWarnings("all")
public class ProductionReportActionTest {

    @Test
    public void testProductionReport() {
        ApplicationContext ac = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
        SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
        ProductionReportAction action = new ProductionReportAction();
        ProductionReportServiceImpl service = new ProductionReportServiceImpl();
        LossOfSpeedDaoImpl dao = new LossOfSpeedDaoImpl();
        dao.setSessionFactory(sessionFactory);
        service.setLossOfSpeedDao(dao);
        action.setProductionReportService(service);
    }

}
