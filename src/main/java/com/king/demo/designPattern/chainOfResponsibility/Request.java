package com.king.demo.designPattern.chainOfResponsibility;

/**
 * Created by ljq on 2019/3/22 13:30
 */
public class Request {
    private RequestType type;
    private String name;

    public Request(RequestType type, String name) {
        this.type = type;
        this.name = name;
    }

    public RequestType getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
