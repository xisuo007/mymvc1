package com.king.demo.designPattern.command;

/**
 * Created by ljq on 2019/3/22 14:17
 */
public class Invoker2 {
    private Command[] commands;
    private final int num =7;

    public Invoker2() {
        this.commands = new Command[num];
    }
    public void setCommand(Command command,int num1){
        commands[num1] = command;
    }
    public void runCommand(int num2){
        commands[num2].execute();
    }
}
