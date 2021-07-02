package com.king.demo.springAOP;

public class RealImplement implements StaticAOP{

    @Override
    public void exec() {
        System.out.println("正常实现接口！");
    }
}