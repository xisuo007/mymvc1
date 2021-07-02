package com.king.util.rsa.codec;

import com.king.util.exception.EncoderException;

/**
 * @description:
 **/
public interface BinaryEncoder extends Encoder {
    public abstract byte[] encode(byte[] paramArrayOfByte)
            throws EncoderException;
}