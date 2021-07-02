package com.king.controller.validation;

import java.lang.annotation.*;

/**
 * @Author ljq
 * @Date 2019/10/30 15:53
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Valid {
    Class<?>[] groups() default {};
}
