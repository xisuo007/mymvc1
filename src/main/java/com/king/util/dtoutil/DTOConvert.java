package com.king.util.dtoutil;

import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;

/**
 * 实体转换类
 * @Author ljq
 * @Date 2019/10/23 14:00
 */
@NoArgsConstructor
public class DTOConvert {

    public static<T,R> R convert(T DTO, Function<T,R> function){
        R record = function.apply(DTO);
        return record;
    }

    public static <T,R> R convert2(T t, Class<R> r, TestDTOConvert<T,R> fun){
        R apply = fun.apply(t, r);
        return apply;
    }

    public static  <T,R> R testDTOConvert(T object,Class<R> ret){
        R r = null;
        try {
            r = ret.newInstance();
            BeanUtils.copyProperties(object,r);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return r;
    }
}
