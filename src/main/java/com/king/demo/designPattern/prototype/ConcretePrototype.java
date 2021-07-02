package com.king.demo.designPattern.prototype;

/**
 * Created by ljq on 2019/3/22 10:52
 */
public class ConcretePrototype extends Prototype {
    private String filed;

    public ConcretePrototype(String filed) {
        this.filed = filed;
    }

    @Override
    Prototype myClone() {
        return new ConcretePrototype(filed);
    }

    @Override
    public String toString() {
        return filed;
    }
}
