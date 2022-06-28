package com.mildlamb.config;

import com.mildlamb.pojo.Lamb;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

// 加载xml配置的bean
@Configuration(proxyBeanMethods = false)
//@Import(MyImportSelector.class)
public class SpringConfig3 {
    @Bean
    public Lamb lamb(){
        return new Lamb();
    }
}
