package com.king.demo.designPattern.decorator;

/**
 * Created by ljq on 2019/4/1 16:36
 */
public class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + 2;
    }
}
