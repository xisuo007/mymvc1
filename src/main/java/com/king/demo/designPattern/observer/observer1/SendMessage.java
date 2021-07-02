package com.king.demo.designPattern.observer.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljq on 2019/4/1 15:30
 */
public class SendMessage implements Subject {
    private List<Observer> observers;
    private String msg;

    public SendMessage() {
        observers = new ArrayList<>();
    }

    public void setMsg(String msg){
        this.msg = msg;
        notifyObserver();
    }

    @Override
    public void register(Observer o) {
        observers.add(o);
    }

    @Override
    public void remove(Observer o) {
        if (observers.indexOf(o) >= 0) {
            observers.remove(o);
        }
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update(msg);
        }
    }
}
