package com.king.controller.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @Author ljq
 * @Date 2019/10/30 9:20
 */
public abstract class BaseAspect {

    /**
     * 获取切点方法
     * @param joinPoint
     * @return
     */
    public Method getMethod(JoinPoint joinPoint){
        Method targetMethod = null;
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            System.out.println(targetName);
            String methodName = joinPoint.getSignature().getName();
            System.out.println(methodName);
            Class<?> targetClass = Class.forName(targetName);
            Class<?>[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
            targetMethod = targetClass.getMethod(methodName,parameterTypes);
            System.out.println("获取切到的方法方法名为："+targetMethod.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取切面方法失败");
        }
    return targetMethod;

    }
}
