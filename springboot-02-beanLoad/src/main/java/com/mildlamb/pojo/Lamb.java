package com.mildlamb.pojo;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component("mildlamb")
// 按照bean进行判定
@ConditionalOnBean(name = {"wildwolf"})
//    @ConditionalOnMissingClass("com.mildlamb.pojo.Kindred")
public class Lamb {
}
