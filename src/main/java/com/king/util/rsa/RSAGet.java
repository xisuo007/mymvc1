package com.king.util.rsa;

import com.king.util.rsa.codec.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * 生成RSA公钥私钥
 */
public class RSAGet {
    public static RsaKeyData genKeyPair(Integer keysize) throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KeyAlgorithmEnum.RSA.getKey());
        keyPairGen.initialize(keysize);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RsaKeyData rsakey = new RsaKeyData(new String(Base64.encodeBase64(publicKey.getEncoded())), new String(Base64.encodeBase64(privateKey.getEncoded())));
        return rsakey;
    }
}