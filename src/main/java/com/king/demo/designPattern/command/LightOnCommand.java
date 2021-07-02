package com.king.demo.designPattern.command;

/**
 * Created by ljq on 2019/3/22 13:56
 */
public class LightOnCommand implements Command {
    Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.on();
    }
}
