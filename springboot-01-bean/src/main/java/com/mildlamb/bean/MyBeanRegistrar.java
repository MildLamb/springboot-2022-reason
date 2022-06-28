package com.mildlamb.bean;

import com.mildlamb.pojo.Wolf;
import com.mildlamb.service.impl.RoleServiceImpl2;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyBeanRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 1. 使用元数据（importingClassMetadata）进行判定 是否需要加载该bean

        // 2. 创建一个beanDefinition
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(RoleServiceImpl2.class).getBeanDefinition();
        beanDefinition.setScope("singleton");

        // 3. 注册BeanDefinition
        registry.registerBeanDefinition("roleService",beanDefinition);
    }
}
