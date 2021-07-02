package com.king.singleton;

/**
 * @Author ljq
 * @Date 2019/12/12 16:42
 */
public class StaticInner1 {
    private static class GetBean{
        public static StaticInner1 bean = new StaticInner1();
    }
    public static StaticInner1 getBean(){
        return GetBean.bean;
    }
}
