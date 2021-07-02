package com.king.demo.designPattern.abstractFactory;

/**
 * Created by ljq on 2019/3/22 10:41
 */
public class ConcreatFactory1 extends AbstractFactory {
    @Override
    AbstractProductA createProductA() {
        return new ProductA1();
    }

    @Override
    AbstractProductB createProductB() {
        return new ProductB1();
    }
}
