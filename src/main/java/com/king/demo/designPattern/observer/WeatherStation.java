package com.king.demo.designPattern.observer;

/**
 * Created by ljq on 2019/3/22 16:39
 */
public class WeatherStation {
    public static void main(String[] args){
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay curr = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay sta = new StatisticsDisplay(weatherData);

        weatherData.setMeasurements(0,0,0);
        weatherData.setMeasurements(1,1,1);
    }
}
