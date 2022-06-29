package com.mildlamb.pojo;

import com.mildlamb.properties.SpiritProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@EnableConfigurationProperties(SpiritProperties.class)
public class Spirit {

    @Autowired
    private SpiritProperties spiritProperties;

    public Spirit(SpiritProperties spiritProperties){
        this.spiritProperties = spiritProperties;
    }

    public void play(){
        System.out.println(spiritProperties.getLamb().getName() + "和" + spiritProperties.getWolf().getName() + "永不分离,他们都是" + spiritProperties.getLamb().getAge());
    }
}
