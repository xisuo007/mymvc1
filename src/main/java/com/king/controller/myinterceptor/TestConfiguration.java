package com.king.controller.myinterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ljq
 * @Date 2019/10/30 12:22
 */
@Configuration
public class TestConfiguration implements WebMvcConfigurer {
    @Bean
    public ValidationInterceptor initValidation(){
        return new ValidationInterceptor();
    }

    @Autowired
    ValidationInterceptor validationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.out.println("=================加入自定义拦截器");
        registry.addInterceptor(validationInterceptor);
    }
}
