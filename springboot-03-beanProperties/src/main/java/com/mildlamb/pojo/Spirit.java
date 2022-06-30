package com.mildlamb.pojo;

import com.mildlamb.properties.SpiritProperties;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@Data
@EnableConfigurationProperties(SpiritProperties.class)
public class Spirit implements ApplicationContextAware {

    @Autowired
    private SpiritProperties spiritProperties;

    public Spirit(SpiritProperties spiritProperties){
        this.spiritProperties = spiritProperties;
    }

    public void play(){
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        System.out.println(spiritProperties.getLamb().getName() + "和" + spiritProperties.getWolf().getName() + "永不分离,他们都是" + spiritProperties.getLamb().getAge());
    }




    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
