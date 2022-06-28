package com.mildlamb.app;

import com.mildlamb.config.SpringConfig3;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App3_3 {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig3.class);

        String[] names = ac.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println("---------------------------");
        SpringConfig3 springConfig3 = ac.getBean("springConfig3", SpringConfig3.class);
        System.out.println(springConfig3.lamb());
        System.out.println(springConfig3.lamb());
        System.out.println(springConfig3.lamb());
    }
}
