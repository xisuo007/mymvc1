package com.king.demo.designPattern.command;

/**
 * Created by ljq on 2019/3/22 14:04
 */
public class Client {
    public static void main(String[] args) {
        Invoker invoker = new Invoker();
        Invoker2 invoker2 = new Invoker2();
        Light light = new Light();
        Command lightOnCommand = new LightOnCommand(light);
        Command lightOffCommand = new LightOffCommand(light);

        //invoker.setOnCommands(lightOnCommand,0);
        //invoker.setOffCommands(lightOffCommand,0);
        //
        //invoker.onButtonWasPushed(0);
        //invoker.offButtonWasPushed(0);

        invoker2.setCommand(lightOffCommand,0);
        invoker2.setCommand(lightOnCommand,1);

        invoker2.runCommand(1);
        invoker2.runCommand(0);




    }
}
