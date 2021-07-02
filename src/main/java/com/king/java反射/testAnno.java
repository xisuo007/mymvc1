package com.king.java反射;


import java.lang.annotation.*;

/**
 * Created by ljq on 2019/7/26 14:39
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface testAnno {
}
