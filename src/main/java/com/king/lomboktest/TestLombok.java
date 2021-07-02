package com.king.lomboktest;

import com.king.poi.hutoolPoi.User;

/**
 * Created by ljq on 2019/5/5 9:22
 */
public class TestLombok {
    public static void main(String[] args){
        /**
         * fluent在构建对象的时候直接用的属性名称，没有get/set
         * chain在构建对象的时候用的是传统的get/set
         */
        Car car = new Car().color("红色").name("bmw");
        car.color("黑色");
        System.out.println(car.color());

        CarChain chain = new CarChain().setColor("蓝色").setName("audi");
        System.out.println(chain.getColor());
        chain.setSize("a4L");
        System.out.println(chain);

        CarChain carChain = new CarChain();
        System.out.println(carChain);
        Car car2 = new Car();
        System.out.println(car2);
        User user = new User();
        System.out.println(user);

    }
}
