package com.king.thread.纤程;

import com.sun.xml.internal.ws.api.pipe.Fiber;

/**
 * Created by ljq on 2020/1/16 16:11
 */
public class TestFiber {

    //纤程相当于是jvm内部的可执行文件路径，普通的Thread需要调用OS的资源来创建(一个大概1M)，
    // 而纤程实际上是栈里的操作(一个大概4k，但是不建议运行耗时较长的操作) 并行计算比较多（需要可能jdk14）
    public static void main(String[] args){
        for (int i = 0; i < 10000; i++) {

        }
    }
}
