package com.mildlamb.config;

import com.mildlamb.bean.MyBeanRegistrar;
import com.mildlamb.bean.MyBeanRegistrar2;
import com.mildlamb.bean.MyPostProcessor;
import com.mildlamb.service.impl.RoleServiceImpl;
import org.springframework.context.annotation.Import;

@Import({MyPostProcessor.class,MyBeanRegistrar2.class,MyBeanRegistrar.class,RoleServiceImpl.class})
public class MyPostProcessorConfig {
}
