package com.king.util.rsa;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {
    public Base64Util() {
    }

    public static String byteToStringBase64(byte[] b) {
        String base64Data = "";

        try {
            base64Data = Base64.encodeBase64String(b);
            if (base64Data != null && base64Data != "") {
                base64Data = base64Data.replaceAll("\\+", "*").replaceAll("\\/", "-");
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return base64Data;
    }

    public static byte[] base64StringToByte(String base64String) {
        if (base64String == null) {
            return null;
        } else {
            base64String = base64String.replaceAll("\\*", "+").replaceAll("-", "/");
            byte[] b = null;
            b = Base64.decodeBase64(base64String);

            for(int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] = (byte)(b[i] + 256);
                }
            }

            return b;
        }
    }

    public static void main(String[] args) {
        String s = "调整异常数据";
        System.err.println(byteToStringBase64(s.getBytes()));
    }
}