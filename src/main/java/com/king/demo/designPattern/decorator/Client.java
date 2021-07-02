package com.king.demo.designPattern.decorator;

/**
 * Created by ljq on 2019/4/1 16:38
 */
public class Client {
    public static void main(String[] args){
        Beverage beverage = new HouseBlend();
        beverage = new Milk(beverage);
        beverage = new Mocha(beverage);

        System.out.println(beverage.cost());
    }
}
