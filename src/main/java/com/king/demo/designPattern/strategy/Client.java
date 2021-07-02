package com.king.demo.designPattern.strategy;

/**
 * Created by ljq on 2019/4/1 15:58
 */
public class Client {
    public static void main(String[] args){
        CarColour colour = new CarColour();
        colour.setCar(new BigCar());
        colour.getCarColour();
        colour.setCar(new SimCar());
        colour.getCarColour();
    }
}
