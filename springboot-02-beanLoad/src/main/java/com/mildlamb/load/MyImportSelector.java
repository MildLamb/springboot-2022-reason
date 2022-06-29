package com.mildlamb.load;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        try {
            Class<?> clazz = Class.forName("com.mildlamb.pojo.Kindred");
            if (clazz != null)
            return new String[]{"com.mildlamb.pojo.Lamb"};
        } catch (ClassNotFoundException e) {
            return new String[]{};
        }
        return null;
    }
}
