package com.king.demo.designPattern.observer;

/**
 * Created by ljq on 2019/3/22 16:49
 * 观察者模式：通过两个接口，其中一个主题接口负责注册/删除/通知 其实现者需要维护一张表和具体的数据，表里放入注册类，需要创建一个方法进行初始化数据，然后再调用通知方法，
 * 另一个接口进行数据更新， 创建的时候用主题接口实现类进行注册初始化，（相当于默认注册进去）实现接口更新方法进行数据操作
 */
public interface Admin {
    void add(Server s);
    void del(Server s);
    void tell();
}
