package com.mildlamb.pojo.factory;

import com.mildlamb.pojo.Wolf;
import org.springframework.beans.factory.FactoryBean;

public class WolfFactoryBean implements FactoryBean<Wolf> {
    @Override
    public Wolf getObject() throws Exception {
        Wolf wolf = new Wolf();
        // ... ...
        return wolf;
    }

    @Override
    public Class<?> getObjectType() {
        return Wolf.class;
    }

    @Override
    public boolean isSingleton(){
        return true;
    }
}
