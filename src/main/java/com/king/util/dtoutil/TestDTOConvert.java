package com.king.util.dtoutil;

/**
 * @Author ljq
 * @Date 2019/10/23 14:10
 */
@FunctionalInterface
public interface TestDTOConvert<T,R> {

   R apply(T t, Class<R> r);
}
