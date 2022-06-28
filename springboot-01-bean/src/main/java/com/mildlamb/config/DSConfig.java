package com.mildlamb.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DSConfig {
    @Bean
    public DruidDataSource dataSource(){
        return new DruidDataSource();
    }
}
