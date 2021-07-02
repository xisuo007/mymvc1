package com.king.util.rsa;

import lombok.Data;

@Data
public class RsaKeyData {
    /**
     * 公钥
     */
    private String publicKey;
    /**
     * 私钥
     */
    private String privateKey;

    public RsaKeyData(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
}