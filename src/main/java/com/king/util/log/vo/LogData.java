package com.king.util.log.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @description: 日志数据对象
 **/
@Data
public class LogData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;//唯一标识
    private String className;//类名
    private String methodName;//方法名
    private String methodDesc;//日志标题
    private String remoteAddr;//请求地址
    private String requestUri; //URI
    private String method;          //请求方式
    private String params;          //提交参数
    private Object outData;          //返回数据
    private Long beginTime;//开始时间
    private Long endTime;//结束时间
    private Long maxMemory;//最大内存
    private Long totalMemory;//已分配内存
    private Long freeMemory;//已分配内存中的剩余空间
}
