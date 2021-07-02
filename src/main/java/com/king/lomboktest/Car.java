package com.king.lomboktest;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by ljq on 2019/5/5 9:13
 * Accessors
   访问器模式，是给一个普通的Bean增加一个便捷的访问器，包括读和写。
   它有两种工作模式，fluent和chain
 */
@Accessors(fluent = true)
@Data
public class Car {
    private String size;
    private String name;
    private String color;
}
