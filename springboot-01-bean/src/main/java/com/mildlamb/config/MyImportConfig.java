package com.mildlamb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(MyImportSelector.class)
@ComponentScan(basePackages = {"com.mildlamb.test"})
public class MyImportConfig {
}
