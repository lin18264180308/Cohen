package com.mrong.lineview.admin.action;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.common.entity.Entry;

/**
 * 六大损失分解测试类
 * 
 * @author 林金成
 *         2017年11月16日
 */
@SuppressWarnings("all")
public class LossDecompositionActionTest {

    @Test
    public void testLoadData() {
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/applicationContext.xml",
                "applicationContext-test.xml");
        LossDecompositionAction action = (LossDecompositionAction) context.getBean("lossDecompositionAction");

        action.setBegin("2017-11-04-14-08");
        action.setEnd("2017-11-17-14-38");
        action.loadData();

        List<Entry<String, Double>> list = action.getLossDecomposition();
        for (Entry<String, Double> entry : list) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

}
