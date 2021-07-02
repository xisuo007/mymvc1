package com.king.singleton;

/**
 * Created by ljq on 2019/9/10 14:45
 * 静态内部类实现单例模式
 */
public class InnerStaticModel {
    private static class SingleTonHolder{
        public static InnerStaticModel model = new InnerStaticModel();
    }
    public static InnerStaticModel getInstance(){
        return SingleTonHolder.model;
    }
}
