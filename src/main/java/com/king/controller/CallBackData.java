package com.king.controller;

import lombok.Data;

@Data
public class CallBackData {
    /**
     * 执行编码
     */
    private String uuid;
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 错误原因
     */
    private String msg;
}