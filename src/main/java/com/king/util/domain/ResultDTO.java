package com.king.util.domain;

import com.king.util.enums.BaseErrorEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: 封装业务成返回数据
 **/
@Data
public class ResultDTO<T> implements Serializable {
    private boolean success = false;
    private T data;
    private String  errMsg;
    private String  errCode;
    private Integer count;
    private String info;

    public void setError(BaseErrorEnum error) {
        this.errCode = error.getErrCode();
        this.errMsg = error.getErrMsg();
    }
}
