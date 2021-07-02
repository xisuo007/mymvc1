package com.king.initBean;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by ljq on 2019/3/15 15:13
 */
@Component
public class InitiaBean {
    /**
     * 1.实例化-填充属性
     * 2.调用BeanNameAware的setBeanName方法
     * 3.调用BeanFactoryAware的setBeanFactory方法
     * 4.调用ApplicationContextAware的setApplicationContext的方法
     * 5.调用BeanPostProcess的postProcessBeforeInitialization方法
     * 6.调用InitializingBean的afterPropertiesSet方法
     * 7.调用定制的初始化方法
     * 8.调用BeanPostPrecess的postProcessAfterInitialization方法
     * 9.Bean准备就绪
     * 10.调用DisposableBean的destroy方法
     * 11.调用定制的销毁方法
     */
    //在 bean 初始化时会经历几个阶段，首先可以使用注解 @PostConstruct, @PreDestroy 来在 bean 的创建和销毁阶段进行调用
    @PostConstruct
    public void start(){
        System.out.println("AnnotationBean start 注解bean  启动");
    }

    @PreDestroy
    public void destroy(){
        System.out.println("AnnotationBean destroy  注解bean  销毁");
    }

}
