package com.king.singleton;

/**
 * Created by ljq on 2019/9/10 14:52
 */
public class EnumSingleton {

    public static EnumSingleton getInstance(){
        return getEnum.INSTANCE.getInstance();
    }

    private enum getEnum{
        INSTANCE;
        private EnumSingleton singleton;
        getEnum(){
            singleton = new EnumSingleton();
        }
        private EnumSingleton getInstance(){
            return singleton;
        }
    }
}
