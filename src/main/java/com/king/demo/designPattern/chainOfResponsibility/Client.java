package com.king.demo.designPattern.chainOfResponsibility;

/**
 * Created by ljq on 2019/3/22 13:41
 * 同样h2调用，但是实际上是各自处理各自的逻辑，h2在调用类型不是h2中类型的时候会把处理交给初始化时持有的h1进行处理
 */
public class Client {
    public static void main(String[] args) {
        Handler h1 = new ConcreteHandler1(null);
        Handler h2 = new ConcreteHandler2(h1);

        Request request1 = new Request(RequestType.TYPE1, "request1：");
        h2.handleRequest(request1);

        Request request2 = new Request(RequestType.TYPE2, "request2：");
        h2.handleRequest(request2);
    }
}