package com.king.lianxi.ret;

import lombok.Data;

/**
 * Created by ljq on 2020/4/28 14:31
 * 默认解析类
 */
@Data
public class DefaultResult<T> extends BaseResult{
    /**
     * 是否成功
     */
    private boolean success = false;
    /**
     * 返回内容
     */
    private T data;
    /**
     * 错误信息
     */
    private String errMsg;
    /**
     * 错误码
     */
    private Integer errCode;
    /**
     * 数量
     */
    private Integer count;
}
