package com.king.demo.designPattern.observer;

/**
 * Created by ljq on 2019/3/22 16:26
 */
public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObserver();
}
