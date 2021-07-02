package com.king.util.enums;

/**
 * http 枚举
 */
public enum HttpMethodEnum {
    POST("POST"),
    GET("GET");
    String method;
    HttpMethodEnum(String method){
        this.method =method;
    }
    
    public String getMethod() {
        return method;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
}
