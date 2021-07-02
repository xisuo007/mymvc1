package com.king.demo.springAOP;

/**
 * Created by ljq on 2019/3/15 15:37
 */
public class ProxyImplement implements StaticAOP {
    private StaticAOP aop;

    public ProxyImplement() {
        this.aop = new RealImplement();
    }

    @Override
    public void exec() {
        System.out.println("静态代理，调用实际方法之前");
        aop.exec();
        System.out.println("静态代理，调用实际方法之后");
    }
    public static void main(String[] args){
        StaticAOP aop = new ProxyImplement();
        aop.exec();
    }
}
