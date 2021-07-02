package com.king.扫描器;

import java.lang.annotation.*;

/**
 * Created by ljq on 2020/7/22 15:56
 * 自定义注解
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnn {
    public String name() default "";
}
