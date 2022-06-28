package com.mildlamb.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

public class MyImportSelector implements ImportSelector {
    @Override
    /*
    * importingClassMetadata 相当于元数据
    * 哪个类上通过@Import导入了当前ImportSelector，谁就是importingClassMetadata
    * */
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 获取元数据全限定类名
        System.out.println(importingClassMetadata.getClassName());
        // 判断元数据是否包含注解
        System.out.println("导入我的类上面是否有@Configuration注解？" + importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration"));
        // 判断元数据中的某个注解中所包含的属性
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes("org.springframework.context.annotation.ComponentScan");
        System.out.println(annotationAttributes);



        // 可以进行各种条件的判定，判定完毕后，再决定是否加载指定的bean
/*        boolean flag = importingClassMetadata.hasAnnotation("org.springframework.context.annotation.Configuration");
        if(flag){
            return new String[]{"com.mildlamb.pojo.Lamb"};
        }

        return new String[]{"com.mildlamb.pojo.Wolf"};*/

        // 参数为要加载的bean的全路径类名
        return new String[]{"com.mildlamb.pojo.Lamb","com.mildlamb.pojo.Wolf"};
    }
}
