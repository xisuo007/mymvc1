package com.king.controller.validation;

import java.lang.annotation.*;

/**
 * @Author ljq
 * @Date 2019/10/24 9:13
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyValid {
    Class<?>[] value() default {};
}
