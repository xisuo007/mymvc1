package com.king.demo.designPattern.factoryMethod;

import com.king.demo.designPattern.simpleFactory.Product;

/**
 * Created by ljq on 2019/3/22 9:57
 */
public abstract class Factory {
    public abstract Product factoryMethod();
    public void doSomething(){
        Product product = factoryMethod();
        //使用product进行操作
    }
}
