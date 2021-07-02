package com.king.util;

import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description: String工具类
 **/
public class StringTools extends StringUtils {
    public static final String EMPTY_STRING     = "";
    public static String arrayToStr(String[] strs, String symbol) {
        return arrayToDelimitedString(strs, symbol);
    }
    public static String arrayToStr(String symbol, String... strs) {
        return arrayToDelimitedString(strs, symbol);
    }

    /**
     * MD5 加密
     * @param piStr
     * @return
     */
    public static String md5(String piStr) {
        if (StringTools.isEmpty(piStr)) {
            piStr = EMPTY_STRING;
        }
        String encodeStr = EMPTY_STRING;
        byte[] digesta = null;
        try {
            MessageDigest alg = MessageDigest.getInstance("MD5");
            alg.update(piStr.getBytes());
            digesta = alg.digest();
            encodeStr = byte2hex(digesta);
        } catch (Exception e) {
        }
        return encodeStr;
    }

    private static String byte2hex(byte[] piByte) {
        String reStr = EMPTY_STRING;
        for (int i = 0; i < piByte.length; i++) {
            int v = piByte[i] & 0xFF;
            if (v < 16) {
                reStr += "0";
            }
            reStr += Integer.toString(v, 16).toLowerCase();
        }
        return reStr;
    }

    /**
     * 前三后四 隐藏手机号
     * @param phoneNo
     * @return
     */
    public static String hidePhoneNo(String phoneNo) {
        if (isEmpty(phoneNo)) {
            return phoneNo;
        }
        if (phoneNo.length() > 7) {
            // 前3后四
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder.append(phoneNo.substring(0, 3)).append("****")
                    .append(phoneNo.substring(phoneNo.length() - 4)).toString();
        } else {
            return phoneNo;
        }
    }

    /**
     * 前六后四 隐藏银行卡号
     * @param cardNo
     * @return
     */
    public static String hideCardNo(String cardNo) {
        if (isEmpty(cardNo)) {
            return cardNo;
        }
        if (cardNo.length() > 10) {
            //前六后四
            StringBuilder stringBuilder = new StringBuilder();
            return stringBuilder.append(cardNo.substring(0, 6)).append("****")
                    .append(cardNo.substring(cardNo.length() - 4)).toString();
        } else {
            return cardNo;
        }
    }

    /**
     * 将传入字符中根据传入分隔符分开，并且返回的是原字符实际分隔数的列表（不管是不是空字符）
     * @param str
     * @param splitStr
     * @return
     */
    public static List<String> splitList(String str, String splitStr) {
        Pattern pattern = Pattern.compile(splitStr);
        Matcher matcher = pattern.matcher(str);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        int len = count + 1;
        List<String> list = new ArrayList<String>(len);
        list.addAll(Arrays.asList(str.split(splitStr)));
        if (list.size() < len) {
            for (int i = list.size(); i < len; i++) {
                list.add(EMPTY_STRING);
            }
        }
        return list;
    }
}
