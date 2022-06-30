package com.mildlamb;

import com.mildlamb.pojo.Spirit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication(excludeName = {"org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration"})
//@Import(Spirit.class)
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(App.class, args);
        Spirit bean = run.getBean(Spirit.class);
        bean.play();
    }
}
