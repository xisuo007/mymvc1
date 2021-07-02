package com.king.demo.designPattern.prototype;

/**
 * Created by ljq on 2019/3/22 10:54
 */
public class Client {
    public static void main(String[] args){
        Prototype prototype = new ConcretePrototype("acb");
        Prototype clone = prototype.myClone();
        System.out.println(clone.toString());
    }
}
