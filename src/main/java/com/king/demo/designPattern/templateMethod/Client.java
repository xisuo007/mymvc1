package com.king.demo.designPattern.templateMethod;

/**
 * Created by ljq on 2019/4/1 16:13
 */
public class Client {
    public static void main(String[] args){
        GoSchool day1 = new Day1();
        day1.begin();
        System.out.println("======================");
        GoSchool day2 = new Day2();
        day2.begin();
    }
}
