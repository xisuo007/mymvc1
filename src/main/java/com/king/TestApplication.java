package com.king;

import com.king.mideng.ApiIdempotentInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ljq on 2019/6/21 17:19
 */
@SpringBootApplication
public class TestApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args){
        SpringApplication.run(TestApplication.class,args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //接口幂等性校验拦截器
        registry.addInterceptor(apiIdempotentInterceptor());
    }

    @Bean
    public ApiIdempotentInterceptor apiIdempotentInterceptor(){
        return new ApiIdempotentInterceptor();
    }
}
