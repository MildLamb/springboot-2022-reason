package com.mildlamb.listener;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class MyListener<T extends ApplicationEvent> implements ApplicationListener<T> {

    @Override
    public void onApplicationEvent(T event) {
        if (event instanceof ApplicationPreparedEvent) {
            System.out.println("============ 这是一个监听器 =============");
            System.out.println(event.getTimestamp());
            System.out.println(event.getSource());
            System.out.println(event.getClass());
        }
    }
}
