package com.king.demo.designPattern.factoryMethod;

import com.king.demo.designPattern.simpleFactory.ConcreteProduct1;
import com.king.demo.designPattern.simpleFactory.Product;

/**
 * Created by ljq on 2019/3/22 10:00
 */
public class ConcreteFactory1 extends Factory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProduct1();
    }
}
