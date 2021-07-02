package com.king.aop;

import com.king.aop.interceptor.TestAopInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by ljq on 2020/5/9 15:58
 */
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    @Bean
    public TestAopInterceptor testAopInterceptor(){return new TestAopInterceptor();}

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(testAopInterceptor());
    }
}
