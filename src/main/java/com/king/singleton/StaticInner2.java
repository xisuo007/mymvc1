package com.king.singleton;

/**
 * Created by ljq on 2020/1/17 10:41
 */
public class StaticInner2 {
    private static class StaticGet{
        public static StaticInner2 bean = new StaticInner2();
    }
    public StaticInner2 get(){
        return StaticGet.bean;
    }
}
