package com.king.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;


/**
 * Created by ljq on 2021-6-30 11:46
 */
@Aspect
@Component
public class MethodTestAnnotationAspect {

    @Before("@annotation(com.king.aop.annotation.TestAnnotationAop)")
    void beforeMethod(){
        System.out.println("切面方法:方法执行前");
    }

    @Around("@annotation(com.king.aop.annotation.TestAnnotationAop)")
    void aroundMethod(ProceedingJoinPoint point) throws Throwable {
        StopWatch watch = new StopWatch("测试切面方法执行时间");
        Class<?> targetClass = point.getTarget().getClass();
        System.out.println("切面方法所在类："+targetClass.getSimpleName());
        String methodName = point.getSignature().getName();
        System.out.println("切面方法名称："+methodName);
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature()).getMethod().getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            System.out.println("切面方法参数类型："+parameterType.getSimpleName());
        }
        Method method = targetClass.getMethod(methodName, parameterTypes);
        System.out.println("切面方法所切的方法："+method);

        Object[] args = point.getArgs();
        for (Object arg : args) {
            System.out.println("切面方法中参数值："+arg.toString());
        }

        watch.start();
        point.proceed();
        watch.stop();
        System.out.println(watch.prettyPrint());
    }
}
