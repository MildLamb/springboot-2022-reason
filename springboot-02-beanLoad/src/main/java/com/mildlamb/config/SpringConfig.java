package com.mildlamb.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mildlamb.load.MyImportSelector;
import com.mildlamb.pojo.Lamb;
import com.mildlamb.pojo.Wolf;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.*;

@Configuration
@Import(Wolf.class)
@ComponentScan({"com.mildlamb.pojo"})
public class SpringConfig {
    // 按照类进行判定
//    @ConditionalOnClass(name = "com.mildlamb.pojo.Kindred")
//    @ConditionalOnMissingClass("com.mildlamb.pojo.Wolf")


/*    // 按照bean进行判定
    @ConditionalOnBean(name = {"wildwolf"})
//    @ConditionalOnMissingClass("com.mildlamb.pojo.Kindred")
    @Bean
    public Lamb lamb(){
        return new Lamb();
    }*/

    @Bean
    @ConditionalOnClass(name = {"com.mysql.jdbc.Driver"})
    public DruidDataSource dataSource(){
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }
}
