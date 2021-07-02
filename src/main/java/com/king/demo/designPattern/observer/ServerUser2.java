package com.king.demo.designPattern.observer;

/**
 * Created by ljq on 2019/3/22 17:00
 */
public class ServerUser2 implements Server {

    public ServerUser2(Admin admin) {
        admin.add(this);
    }

    @Override
    public void update(String notice) {
        System.out.println("serverUser2:============"+notice);
    }
}
