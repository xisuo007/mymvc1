package com.king.java反射;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * Created by ljq on 2019/7/26 14:39
 * getMethods()方法获取的所有方法
 * getDeclaredMethods()方法获取的所有方法
 *
 * getFiled：访问公有的成员变量
 * getDeclaredField：所有已声明的成员变量，但不能得到其父类的成员变量
 */
//@Component
public class test implements BeanPostProcessor {

    @Resource
    ApplicationContext context;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        try {
            Object target = bean;
            System.out.println(bean.getClass().getSimpleName());
            Class<?> targetClass;
            targetClass = bean.getClass();
            if (bean instanceof Advised) {
                System.out.println("如果是代理对象");
                TargetSource targetSource = ((Advised) bean).getTargetSource();
                System.out.println("通过getTargetSource方法获取代理目标："+targetSource);
                target = targetSource.getTarget();
                System.out.println("通过getTarget获取当前代理对象的真正目标对象（可能还是一个代理对象）"+target);
                targetClass = targetSource.getTargetClass();
                System.out.println("获得targetClass:"+targetClass);
            }

            if (target != null && targetClass != null) {
                Field[] fields = targetClass.getDeclaredFields();
                System.out.println("如果该类存在，获取该类的所有属性包含私有属性："+fields[0].getName());
                if (fields.length > 0) {
                    for (Field field : fields) {
                        if (field.getAnnotation(testAnno.class) != null) {
                            System.out.println("如果获取该成员上的注解类型和自己指定的一致");
                            Type genericType = field.getGenericType();
                            System.out.println("获取该成员的Type类型");
                            if (genericType instanceof ParameterizedType) {
                                System.out.println("判断该成员的类型是不是带泛型的类型");
                                ParameterizedType genericType2 = (ParameterizedType)genericType;
                                System.out.println("强制转换为带泛型的Type");
                                Type[] actualTypeArguments = genericType2.getActualTypeArguments();
                                System.out.println("获取该成员的泛型列表");
                                if (actualTypeArguments.length >0) {
                                    String typeName = actualTypeArguments[0].getTypeName();
                                    System.out.println("获取该泛型类型的名字："+typeName);
                                    boolean accessible = field.isAccessible();
                                    System.out.println("查看该成员是不是私有的："+accessible);
                                    System.out.println("把需要的属性设置进去");
                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bean;
    }

}
