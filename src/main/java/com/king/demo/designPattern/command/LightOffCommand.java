package com.king.demo.designPattern.command;

/**
 * Created by ljq on 2019/3/22 13:58
 */
public class LightOffCommand implements Command {
    Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.off();
    }
}
