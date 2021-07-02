package com.king.util.rsa.codec;

import com.king.util.exception.EncoderException;

/**
 * @description: 加密
 **/
public interface Encoder {
    public abstract Object encode(Object paramObject) throws EncoderException;
}
