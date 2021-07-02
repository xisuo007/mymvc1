package com.king.demo.designPattern.command;

/**
 * Created by ljq on 2019/3/22 13:55
 * 命令模式：每一个实现了command的类都是一个具体的命令，通过invoker把命令设置到命令数组中（相当于给遥控器按钮指定命令），调用invoker中的execute来执行具体的命令
 * 添加命令的时候需要在具体的方法类中添加方法，然后由command的一个子类进行调用。
 */
public interface Command {
    void execute();
}
