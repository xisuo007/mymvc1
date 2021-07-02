package com.king.demo.designPattern.observer;

/**
 * Created by ljq on 2019/3/22 16:35
 */
public class StatisticsDisplay implements Observer {

    public StatisticsDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }

    @Override
    public void update(float temp, float hubidity, float pressure) {
        System.out.println("StatisticsDisplay.update: " + temp + " " + hubidity + " " + pressure);
    }
}
