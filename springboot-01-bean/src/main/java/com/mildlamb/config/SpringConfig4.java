package com.mildlamb.config;

import com.mildlamb.pojo.Lamb;
import com.mildlamb.pojo.Wolf;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({Lamb.class, Wolf.class,DSConfig.class})
public class SpringConfig4 {
}
