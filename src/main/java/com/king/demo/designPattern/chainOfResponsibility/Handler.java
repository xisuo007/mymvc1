package com.king.demo.designPattern.chainOfResponsibility;

/**
 * Created by ljq on 2019/3/22 13:29
 * 责任链模式，抽象类本身持有一个自身的引用   实现类中进行初始化，实现抽象方法中继续用持有的--其他实现了同样接口的类--进行调用，
 * 达到每个实现了抽象方法的类都有机会（如果判断是自己需要处理的时候，相当于是谁负责的谁来处理）对request进行处理。
 */
public abstract class Handler {
    protected Handler successor;

    public Handler(Handler successor) {
        this.successor = successor;
    }

    protected abstract void handleRequest(Request request);
}
