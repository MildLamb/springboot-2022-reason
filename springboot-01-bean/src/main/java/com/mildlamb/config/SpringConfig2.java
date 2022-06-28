package com.mildlamb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

// 加载xml配置的bean
@Configuration
@ImportResource({"bean.xml","bean2.xml"})
@ComponentScan({"com.mildlamb.pojo"})
public class SpringConfig2 {
}
