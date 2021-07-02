package com.king.util.enums;

/**
 * @description:
 **/
public enum SignatureErrorEnum implements BaseErrorEnum{
    TRACE_ERROR("203000","数据转换失败"),
    TIMEFORMAT_ERROR("203001","时间格式化失败"),

    ;
    private final String errCode;
    private final String errMsg;

    SignatureErrorEnum(String errCode, String errMsg){
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
