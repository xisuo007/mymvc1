package com.king.util.exception;


import com.king.util.enums.BaseErrorEnum;
import lombok.Getter;

/**
 * @description: 业务异常类
 **/
public class BusinessMsgException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    @Getter
    String errCode;
    @Getter
    String errMsg;
    public BusinessMsgException(String errCode, String errMsg){
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public BusinessMsgException(String errCode, String errMsg, Throwable t){
        super(t);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    public BusinessMsgException(BaseErrorEnum error){
        super(error.getErrMsg());
        this.errCode = error.getErrCode();
        this.errMsg = error.getErrMsg();
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
