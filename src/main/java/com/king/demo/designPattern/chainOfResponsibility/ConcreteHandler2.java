package com.king.demo.designPattern.chainOfResponsibility;

/**
 * Created by ljq on 2019/3/22 13:37
 */
public class ConcreteHandler2 extends Handler {
    public ConcreteHandler2(Handler successor) {
        super(successor);
    }

    @Override
    protected void handleRequest(Request request) {
        if (request.getType() == RequestType.TYPE2) {
            System.out.println(request.getName()+"is handle by concretehandler2");
            return;
        }
        if (successor != null) {
            successor.handleRequest(request);
        }
    }
}
