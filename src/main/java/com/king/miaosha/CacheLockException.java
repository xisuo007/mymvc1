package com.king.miaosha;

import lombok.Data;

/**
 * Created by ljq on 2019/6/24 16:33
 */
@Data
public class CacheLockException extends Throwable {
    private String msg;

    public CacheLockException(String msg) {
        this.msg = msg;
    }

    public CacheLockException() {}
}
