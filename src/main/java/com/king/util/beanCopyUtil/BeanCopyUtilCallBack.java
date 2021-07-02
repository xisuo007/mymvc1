package com.king.util.beanCopyUtil;


/**
 * Created by ljq on 2020/1/14 17:13
 */
@FunctionalInterface
public interface BeanCopyUtilCallBack<S,T> {
    void callBack(S s, T t);
}
