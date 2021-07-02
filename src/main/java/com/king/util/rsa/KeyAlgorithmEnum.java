package com.king.util.rsa;

public enum KeyAlgorithmEnum {
    RSA("RSA", "RSA"),
    RSA2("RSA2", "RSA2");
    private final String key;
    private final String des;

    KeyAlgorithmEnum(String key, String des) {
        this.des = des;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getDes() {
        return des;
    }
}