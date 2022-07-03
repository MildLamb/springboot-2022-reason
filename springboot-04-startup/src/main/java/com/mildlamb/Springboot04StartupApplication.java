package com.mildlamb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Properties;

@SpringBootApplication
public class Springboot04StartupApplication {

    public static void main(String[] args) {
        args = new String[]{"test.value=kindred"};
        ConfigurableApplicationContext run = SpringApplication.run(Springboot04StartupApplication.class, args);
//        Properties properties = System.getProperties();
//        properties.list(System.out);
    }

}
