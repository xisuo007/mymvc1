package com.king.util.rsa.codec;

import com.king.util.exception.DecoderException;

/**
 * @description:
 **/
public abstract interface BinaryDecoder extends Decoder {
    public abstract byte[] decode(byte[] paramArrayOfByte)
            throws DecoderException;
}
