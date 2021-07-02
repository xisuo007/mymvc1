package com.king.util.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class RSAUtil {
    private static final String ENCODE = "UTF-8";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";

    public RSAUtil() {
    }

    public static Map<String, Object> genKeyPair() {
        String pubKey = "";
        String prvKey = "";
        HashMap keyMap = new HashMap();

        try {
            KeyPair keypair = generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey)keypair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey)keypair.getPrivate();
            pubKey = Base64Util.byteToStringBase64(publicKey.getEncoded());
            prvKey = Base64Util.byteToStringBase64(privateKey.getEncoded());
            keyMap.put("publicKey", pubKey);
            keyMap.put("privateKey", prvKey);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return keyMap;
    }

    private static KeyPair generateKeyPair() throws Exception {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(1024);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            return keyPair;
        } catch (Exception var3) {
            throw new Exception(var3.getMessage());
        }
    }

    private static byte[] encryptByPublicKey(String data, String publicKey) throws Exception {
        byte[] encryptedData = data.getBytes("UTF-8");
        byte[] keyBytes = Base64Util.base64StringToByte(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(1, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
            byte[] cache;
            if (inputLen - offSet > 117) {
                cache = cipher.doFinal(encryptedData, offSet, 117);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    private static byte[] decryptByPrivateKey(String data, String privateKey) throws Exception {
        byte[] encryptedData = Base64.decodeBase64(data);
        byte[] keyBytes = Base64Util.base64StringToByte(privateKey);
        System.out.println(new String(keyBytes,"utf-8"));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(2, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
            byte[] cache;
            if (inputLen - offSet > 128) {
                cache = cipher.doFinal(encryptedData, offSet, 128);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 使用公钥进行加密
     * @param data 数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, String publicKey) {
        String encryptString = "";
        if (isNotEmpty(data)) {
            try {
                byte[] encryptData = encryptByPublicKey(data, publicKey);
                encryptString = Base64.encodeBase64String(encryptData);
                return encryptString;
            } catch (Exception var4) {
                var4.printStackTrace();
            }
        }

        return encryptString;
    }

    /**
     * 用私钥进行解密
     * @param data 加密数据
     * @param privateKey 解密私钥
     * @return
     */
    public static String decrypt(String data, String privateKey) {
        String decryptString = "";

        try {
            byte[] decryptData = decryptByPrivateKey(data, privateKey);
            decryptString = new String(decryptData, "UTF-8");
            return decryptString;
        } catch (Exception var4) {
            var4.printStackTrace();
            return decryptString;
        }
    }

    public static final boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static final boolean isEmpty(String str) {
        return str == null || str.length() == 0 || "null".equals(str);
    }
}
