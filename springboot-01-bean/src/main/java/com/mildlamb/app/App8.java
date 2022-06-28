package com.mildlamb.app;

import com.mildlamb.config.MyPostProcessorConfig;
import com.mildlamb.service.RoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App8 {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(MyPostProcessorConfig.class);
        RoleService roleService = ac.getBean("roleService",RoleService.class);
        roleService.check();
    }
}
