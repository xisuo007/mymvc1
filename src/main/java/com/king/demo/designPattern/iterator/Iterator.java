package com.king.demo.designPattern.iterator;

/**
 * Created by ljq on 2019/3/22 15:28
 */
public interface Iterator<Item> {
    Item next();
    boolean hasNext();
}
