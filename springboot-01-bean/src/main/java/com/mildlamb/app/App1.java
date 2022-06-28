package com.mildlamb.app;

import com.mildlamb.pojo.Lamb;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App1 {
    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
//        Lamb lamb = ac.getBean("lamb", Lamb.class);
//        System.out.println(lamb);

        String[] names = ac.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
