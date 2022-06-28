package com.mildlamb.config;

import com.mildlamb.bean.MyBeanRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyBeanRegistrar.class)
public class MyBeanRegistrarConfig {
}
