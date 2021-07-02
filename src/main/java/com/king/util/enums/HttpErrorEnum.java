package com.king.util.enums;

/**
 * @description: http 异常信息
 **/
public enum HttpErrorEnum implements BaseErrorEnum{

    HTTPCLIENT_ERROR("500","http链接失败")
    ;
    private final String errCode;
    private final String errMsg;

    HttpErrorEnum(String errCode, String errMsg){
        this.errMsg = errMsg;
        this.errCode = errCode;
    }
    @Override
    public String getErrCode() {
        return errCode;
    }
    @Override
    public String getErrMsg() {
        return errMsg;
    }
}
