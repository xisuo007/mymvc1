package com.king.aop.annotation;

import java.lang.annotation.*;

/**
 * Created by ljq on 2021-6-30 11:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAnnotationAop {
    String description() default "";
}
