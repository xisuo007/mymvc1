package com.king.util.log.annotation;

import java.lang.annotation.*;

/**
 * @description: 自定义注解 拦截Controller
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemControllerLog {
    String description() default "";
}
