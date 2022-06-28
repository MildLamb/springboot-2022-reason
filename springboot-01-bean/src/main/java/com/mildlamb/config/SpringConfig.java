package com.mildlamb.config;

import com.mildlamb.pojo.Wolf;
import com.mildlamb.pojo.factory.WolfFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.mildlamb.pojo","com.mildlamb.config"})
public class SpringConfig {
    @Bean
    public WolfFactoryBean wolf(){
        return new WolfFactoryBean();
    }
}
