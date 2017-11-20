package com.mrong.lineview.admin.action;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mrong.lineview.common.entity.Entry;

/**
 * 因果损失报表测试方法
 * 
 * @author 林金成
 *         2017年11月16日
 */
@SuppressWarnings("all")
public class CausalLossActionTest {

    @Test
    public void testLoadData() {
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/applicationContext.xml",
                "applicationContext-test.xml");
        CausalLossAction action = (CausalLossAction) context.getBean("causalLossAction");

        action.setBegin("2017-11-16-14-08");
        action.setEnd("2017-11-16-14-38");
        action.loadData();
        List<Entry<String, Double>> list = action.getCausalLoss();
        for (Entry<String, Double> entry : list) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

}
