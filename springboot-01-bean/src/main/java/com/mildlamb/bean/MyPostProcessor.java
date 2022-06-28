package com.mildlamb.bean;

import com.mildlamb.pojo.Wolf;
import com.mildlamb.service.impl.RoleServiceImpl4;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class MyPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        // 创建一个beanDefinition
        BeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(RoleServiceImpl4.class).getBeanDefinition();
        beanDefinition.setScope("singleton");

        // 注册BeanDefinition
        beanDefinitionRegistry.registerBeanDefinition("roleService",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
