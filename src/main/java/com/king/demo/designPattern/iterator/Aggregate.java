package com.king.demo.designPattern.iterator;

/**
 * Created by ljq on 2019/3/22 15:28
 * 迭代器有两个接口，Aggregate是聚合类，抽象方法产生一个迭代器（接口），
 *                Iterator 主要定义了hasNext（）和Next（）方法
 *                实现Aggregate的方法中维护一个整型数组，并在初始化函数中进行初始化，实现的抽象方法中返回一个Iterator的具体实现
 *                实现Iterator的方法中需要一个泛型数组，来进行初始化
 */
public interface Aggregate {
    Iterator createIterator();
}
