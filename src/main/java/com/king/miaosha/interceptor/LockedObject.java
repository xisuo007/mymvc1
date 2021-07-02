package com.king.miaosha.interceptor;

import java.lang.annotation.*;

/**
 * Created by ljq on 2019/6/24 16:11
 * 用于参数级的注解，   用于注解商品id等基本类型的参数
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockedObject {
    //不需要值
}
