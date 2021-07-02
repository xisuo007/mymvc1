package com.king.demo.designPattern.templateMethod;

/**
 * Created by ljq on 2019/4/1 16:12
 */
public class Day2 extends GoSchool {
    @Override
    void eat() {
        System.out.println("吃馒头和咸菜");
    }

    @Override
    void howTo() {
        System.out.println("爸爸开车送我去学校");
    }
}
