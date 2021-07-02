package com.king.demo.designPattern.observer.observer1;

/**
 * Created by ljq on 2019/4/1 15:40
 */
public class TestMsg {
    public static void main(String[] args){
        SendMessage msg = new SendMessage();
        GetMsg1 msg1 = new GetMsg1(msg);
        GetMsg2 msg2 = new GetMsg2(msg);
        msg.setMsg("测试观察者模式---------");
        msg.setMsg("测试观察者模式+++++++++");

    }
}
