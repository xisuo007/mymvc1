package com.king.demo.designPattern.strategy;

/**
 * Created by ljq on 2019/4/1 15:55
 */
public class CarColour {
    private Car car;
    public void getCarColour(){
        if (car != null) {
            car.getCol();
        }
    }

    public void setCar(Car car){
        this.car = car;
    }
}
