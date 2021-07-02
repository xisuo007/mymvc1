package com.king.demo.designPattern.simpleFactory;

/**
 * Created by ljq on 2019/3/22 9:45
 * 简单工厂  通过一个单独的类来返回所需要的子类实例，子类都实现同一个接口
 */
public class SimpleFactory {
    public Product creatProduct(int type){
        if (type ==1) {
            return new ConcreteProduct1();
        }else if (type ==2){
            return new ConcreteProduct2();
        }
        return new ConcreteProduct();
    }
}
