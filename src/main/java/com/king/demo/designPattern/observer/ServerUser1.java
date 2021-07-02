package com.king.demo.designPattern.observer;

/**
 * Created by ljq on 2019/3/22 16:58
 */
public class ServerUser1 implements Server {
    public ServerUser1(Admin admin) {
        admin.add(this);
    }

    @Override
    public void update(String notice) {
        System.out.println("serverUser1:--------"+notice);
    }
}
