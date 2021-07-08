package com.king.util.dao;

import java.lang.annotation.*;

/**
 * Created by ljq on 2020/3/3 9:26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectDAO {
    /**
     * 多数据源时的 sqlSessionFactoryName
     * @return
     */
    String sqlSessionFactoryName() default "";
}
