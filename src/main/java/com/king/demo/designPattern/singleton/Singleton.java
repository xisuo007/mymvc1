package com.king.demo.designPattern.singleton;


/**
 * Created by ljq on 2019/3/21 17:23
 */
public class Singleton {
    private Singleton singleton;
    private Singleton() {
    }
    private static class SingletonGet{
        private static final Singleton ret = new Singleton();
    }
    public static Singleton get(){
        return SingletonGet.ret;
    }
}
