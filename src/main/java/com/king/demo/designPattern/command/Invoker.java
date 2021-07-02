package com.king.demo.designPattern.command;


/**
 * Created by ljq on 2019/3/22 13:59
 */
public class Invoker {
    private Command[] onCommands;
    private Command[] offCommands;
    private final int slotNum = 7;

    public Invoker() {
        this.onCommands = new Command[slotNum];
        this.offCommands = new Command[slotNum];
    }
    public void setOnCommands(Command command,int slot){
        onCommands[slot] = command;
    }
    public void setOffCommands(Command command,int slot){
        offCommands[slot] = command;
    }
    public void onButtonWasPushed(int slot){
        onCommands[slot].execute();
    }
    public void offButtonWasPushed(int slot){
        offCommands[slot].execute();
    }

}
