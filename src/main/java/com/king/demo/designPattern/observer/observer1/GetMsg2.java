package com.king.demo.designPattern.observer.observer1;

/**
 * Created by ljq on 2019/4/1 15:37
 */
public class GetMsg2 implements Observer {
    public GetMsg2(Subject msg) {
        msg.register(this);
    }

    @Override
    public void update(String msg) {
        System.out.println("msg2:"+msg);
    }
}
