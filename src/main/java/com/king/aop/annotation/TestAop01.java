package com.king.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by ljq on 2020/5/9 15:34
 */
@Target(ElementType.METHOD)//用在方法上
@Retention(RetentionPolicy.RUNTIME)//运行时
@Documented
public @interface TestAop01 {
    String description() default "";
}
