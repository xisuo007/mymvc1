package com.king.util.exception;

import com.king.util.enums.BaseErrorEnum;
import com.king.util.enums.SignatureErrorEnum;
import com.king.util.exception.BusinessMsgException;

/**
 * @description: 加密异常
 **/
public class EncoderException extends BusinessMsgException {
    private static final long serialVersionUID = 639276068312354225L;

    public EncoderException(String errCode, String errMsg){
        super(errCode,errMsg);
    }

    public EncoderException(String errCode, String errMsg, Throwable t){
        super(errCode,errMsg,t);
    }
    public EncoderException(BaseErrorEnum error){
        super(error);
    }
    public EncoderException(String errMsg){
        super(SignatureErrorEnum.TRACE_ERROR.getErrCode(),errMsg);
    }
}