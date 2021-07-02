package com.king.demo.designPattern.abstractFactory;

/**
 * Created by ljq on 2019/3/22 10:43
 */
public class ConcreatFactory2 extends AbstractFactory {
    @Override
    AbstractProductA createProductA() {
        return new ProductA2();
    }

    @Override
    AbstractProductB createProductB() {
        return new ProductB2();
    }
}
