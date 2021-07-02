package com.king.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Created by ljq on 2020/5/9 15:36
 * spring aop中的五种通知类型
 * Aspect 代表要执行的方法
 */
@Aspect
@Component
public class MethodTestAop01Aspect {

    public MethodTestAop01Aspect() {
    }

    @Pointcut("@annotation(com.king.aop.annotation.TestAop01)")
    public void MethodTestAop01Aspect(){}

    /**
     * 1.前置通知，在切点前面执行，前置通知不会影响切点的执行，除非抛出异常
     */
    @Before("MethodTestAop01Aspect()")
    public void before(){
        System.out.println("切点前置通知");
    }

    /**
     * 2.后置通知，在切点执行完成后执行，不管是正常执行完成，还是抛出异常，都会执行返回通知的内容
     */
    @After("MethodTestAop01Aspect()")
    public void after(){
        System.out.println("切点后置通知");
    }

    /**
     *3.正常返回通知，在切点正常执行完成后执行，如果切点抛出异常，则不会执行。
     */
    @AfterReturning("MethodTestAop01Aspect()")
    public void afterReturn(){
        System.out.println("切点正常返回通知");
    }

    /**
     * 4.异常返回通知，在切点抛出异常后执行
     */
    @AfterThrowing(value = "MethodTestAop01Aspect()",throwing = "ex")
    public void afterThrowing(Throwable ex){
        System.out.println("切点异常返回通知");
    }

    /**
     * 5.环绕通知，环绕在切点前后，比如一个方法调用的前后。这个是最强大的通知类型，能在方法调用前后自定义一些操作。
     */
    @Around("MethodTestAop01Aspect()")
    public Object doAround(ProceedingJoinPoint point){
        String s = point.getTarget().toString();
        System.out.println(s);
        System.out.println("环绕通知");
        return "环绕通知";
    }
}
