package com.king.util.exception;


import com.king.util.enums.BaseErrorEnum;
import com.king.util.enums.SignatureErrorEnum;

/**
 * @description: 解密异常
 **/
public class DecoderException extends BusinessMsgException {
    private static final long serialVersionUID = -8524444543640582985L;

    public DecoderException(String errCode, String errMsg){
        super(errCode,errMsg);
    }

    public DecoderException(String errCode, String errMsg, Throwable t){
        super(errCode,errMsg,t);
    }
    public DecoderException(BaseErrorEnum error){
        super(error);
    }
    public DecoderException(String errMsg){
        super(SignatureErrorEnum.TRACE_ERROR.getErrCode(),errMsg);
    }
}
