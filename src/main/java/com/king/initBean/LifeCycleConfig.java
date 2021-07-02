package com.king.initBean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ljq on 2019/3/18 12:13
 */
@Configuration
public class LifeCycleConfig {
    @Bean(initMethod = "start", destroyMethod = "destroy")
    public SpringLifeCycle create(){
        SpringLifeCycle cycle = new SpringLifeCycle();
        return cycle;
    }
}
