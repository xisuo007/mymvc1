package com.king.hbase.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Created by ljq on 2020-9-18 16:50
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName){
        Assert.notNull(beanName,"bean名称不能为空");
        assertApplicationContext();
        return (T)applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType){
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext(){
        if (SpringContextHolder.applicationContext == null) {
            throw new RuntimeException("applicationContext属性为null，请检查是否注入了");
        }
    }
}
