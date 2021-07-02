package com.king.java8.lambda;


import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Created by ljq on 2019/7/19 9:00
 */
public class TestLambda {
    List<User> users = Arrays.asList(new User(101,"张三",18,333.34),
            new User(102,"李四",19,343.34),
            new User(103,"王五",20,353.34),
            new User(104,"赵六",16,363.34),
            new User(105,"田七",17,383.34));
    public static void main(String[] args){
        TestLambda test = new TestLambda();
        //test.test1();
        //test.users.forEach(System.out::println);

        //test.test2();
        Object json = JSON.toJSON(new User(102, "李四", 19, 343.34));
        System.out.println(json);
    }

    /**
     * 定制排序
     */
    public void test1(){
        Collections.sort(users,(e1,e2)->{
            if (e1.getAge() ==e2.getAge()) {
                return e1.getName().compareTo(e2.getName());
            }else {
                return Integer.compare(e1.getAge(),e2.getAge());
            }
        });
    }

    /**
     * 用四大函数接口之一    Function来处理
     */
    public void test2(){
        System.out.println("\t\t\t  好好学习");
        String s = strHandler("\t\t\t  好好学习", (str) -> str.trim());//去除空格
        System.out.println(s);

        String s1 = strHandler("adkaf", str -> str.toUpperCase());//转大写
        System.out.println(s1);

        String s2 = strHandler("好好学习，天天向上", str -> str.substring(2, 4));
        System.out.println(s2);

    }

    /**
     * 对传入的参数进行处理
     */
    public<T,R> R strHandler(T str, Function<T,R> fun){
        return fun.apply(str);
    }

    /**
     * 对传入的参数进行处理
     */
    public<T,R> R strHandler2(T str, Function<T,R> fun){
        return fun.apply(str);
    }

}
