package com.king.initBean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;

/**
 * Created by ljq on 2019/3/18 13:42
 */

public class Person implements BeanFactoryAware,BeanNameAware, InitializingBean,DisposableBean {
    private String name;
    private String address;
    private int phone;

    private BeanFactory beanFactory;
    private String beanName;

    public Person() {
        System.out.println("调用Persion的构造器实例化");
    }

    // 这是BeanFactoryAware接口方法
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("BeanFactoryAware接口  调用BeanFactoryAware.setBeanFactory()");
    }

    // 这是BeanNameAware接口方法
    @Override
    public void setBeanName(String s) {
        System.out.println("BeanNameAware接口  调用BeanNameAware.setBeanName");
    }

    // 这是InitializingBean接口方法
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean接口  调用InitializingBean.afterPropertiesSet()");
    }

    // 这是DisposableBean接口方法
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean接口  调用DisposableBean.destroy()");
    }

    public void myInit(){
        System.out.println("调用bean的init-method属性指定的初始化方法");
    }
    public void myDestroy(){
        System.out.println("调用bean的destroy-method属性指定的初始化方法");
    }
}
