package com.king.demo.designPattern.observer.observer1;

/**
 * Created by ljq on 2019/4/1 15:37
 */
public class GetMsg1 implements Observer {
    public GetMsg1(Subject msg) {
        msg.register(this);
    }

    @Override
    public void update(String msg) {
        System.out.println("msg1:"+msg);
    }
}
