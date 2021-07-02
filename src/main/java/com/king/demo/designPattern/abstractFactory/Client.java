package com.king.demo.designPattern.abstractFactory;

/**
 * Created by ljq on 2019/3/22 10:43
 */
public class Client {
    public static void main(String[] args){
        AbstractFactory abstractFactory = new ConcreatFactory1();
        AbstractProductA productA = abstractFactory.createProductA();
        AbstractProductB productB = abstractFactory.createProductB();
    }
}
