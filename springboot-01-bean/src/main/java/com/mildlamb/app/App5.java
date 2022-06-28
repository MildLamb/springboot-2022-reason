package com.mildlamb.app;

import com.mildlamb.config.SpringConfig4;
import com.mildlamb.pojo.Kindred;
import com.mildlamb.pojo.Lamb;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App5 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig4.class);

        // 上下文容器对象已经初始化完毕后，手工加载bean
        ac.registerBean("mildlamb", Kindred.class,0);
        ac.registerBean("mildlamb", Kindred.class,1);
        ac.registerBean("mildlamb", Kindred.class,2);
        ac.register(Lamb.class);

        String[] names = ac.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }

        System.out.println("-----------------");
        System.out.println(ac.getBean(Kindred.class));
    }
}
