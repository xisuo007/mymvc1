package com.king.demo.designPattern.templateMethod;

/**
 * Created by ljq on 2019/4/1 16:12
 */
public class Day1 extends GoSchool {
    @Override
    void eat() {
        System.out.println("吃牛奶和面包");
    }

    @Override
    void howTo() {
        System.out.println("骑自行车去学校");
    }
}
