package com.king.miaosha.interceptor;

import java.lang.annotation.*;

/**
 * Created by ljq on 2019/6/24 16:16
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockedComplexObject {
    String field() default "";  //含有成员变量的复杂对象中需要加锁的成员变量，如一个商品对象的商品id
}
