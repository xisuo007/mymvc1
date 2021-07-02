package com.king.util.rsa.codec;

import com.king.util.exception.DecoderException;

/**
 * @description: 解密
 **/
public interface Decoder {
    public abstract Object decode(Object paramObject)
            throws DecoderException;
}
