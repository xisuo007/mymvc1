package com.king.java反射;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

/**
 * Created by ljq on 2019/7/26 14:42
 */
@Data
@Component
public class testBean {

    @testAnno
    private String name;

    @testAnno
    private Consumer<String> consumer;

    private Integer age;

    public String test01(){
        return "test01-01";
    }

    private String test02(){
        return "test02-02";
    }
}
