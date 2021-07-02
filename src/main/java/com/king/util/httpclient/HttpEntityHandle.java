package com.king.util.httpclient;

import org.apache.http.HttpEntity;

/**
 * @description: 处理类
 **/
public interface HttpEntityHandle<T> {
    /**
     * http回调处理
     * @param entity
     */
    T invoke(HttpEntity entity) throws Exception;
}
