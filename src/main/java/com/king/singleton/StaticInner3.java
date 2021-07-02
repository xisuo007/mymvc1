package com.king.singleton;

/**
 * Created by ljq on 2020/3/3 9:18
 */
public class StaticInner3 {
    private static class SonClass{
        public static StaticInner3 bean = new StaticInner3();
    }

    public StaticInner3 getSingleton(){
        return SonClass.bean;
    }
}
