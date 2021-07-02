package com.king.lomboktest;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by ljq on 2020/3/17 17:40
 */
@AllArgsConstructor
@Data
public class CatTest<T> {
    /**
     * 接口编码
     */
    private String operateCode;

    private T t;
}
