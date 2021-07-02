package com.king.demo.designPattern.observer.observer1;

/**
 * Created by ljq on 2019/4/1 15:25
 */
public interface Subject {
    void register(Observer o);
    void remove(Observer o);
    void notifyObserver();
}
