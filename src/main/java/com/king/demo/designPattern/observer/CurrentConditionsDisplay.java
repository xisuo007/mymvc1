package com.king.demo.designPattern.observer;

/**
 * Created by ljq on 2019/3/22 16:37
 */
public class CurrentConditionsDisplay implements Observer {

    public CurrentConditionsDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float hubidity, float pressure) {
        System.out.println("CurrentConditionsDisplay.update: " + temp + " " + hubidity + " " + pressure);
    }
}
