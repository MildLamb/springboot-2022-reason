package com.mildlamb.app;

import com.mildlamb.config.MyImportConfig;
import com.mildlamb.config.SpringConfig3;
import com.mildlamb.config.SpringConfig4;
import com.mildlamb.pojo.Kindred;
import com.mildlamb.pojo.Lamb;
import com.mildlamb.pojo.Wolf;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App6 {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyImportConfig.class);
        String[] names = ac.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        System.out.println("-----------------");
//        System.out.println(ac.getBean(Lamb.class));
//        System.out.println(ac.getBean(Wolf.class));
    }
}
