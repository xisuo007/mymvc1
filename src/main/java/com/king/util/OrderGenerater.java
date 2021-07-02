package com.king.util;


import com.king.util.log.LogUtil;
import org.junit.platform.commons.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: 订单编号生成规则
 **/
public class OrderGenerater {
    private volatile static int serialNo = 0;
    private static final String FORMATSTRING = "yyyyMMddHHmmssSSS";
    /**
     * 使用公平锁防止饥饿
     */
    private static final Lock lock = new ReentrantLock(true);

    /**
     * * 生成订单号，生成规则 时间戳+机器IP最后两位+2位随机数+两位自增序列 <br>
     * * 采用可重入锁减小锁持有的粒度，提高系统在高并发情况下的性能 * *
     *
     * @return
     */
    public static String generateOrderNo() {
        StringBuilder builder = new StringBuilder();
        builder.append(getDateTime(FORMATSTRING));
        builder.append(getLastNumOfIP());
        builder.append(getRandomNum());
        builder.append(getIncrement());
        return builder.toString();
    }

    /**
     * 获取系统当前时间
     *
     * @param formatStr
     * @return
     */
    private static String getDateTime(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date());
    }

    /**
     * 获取自增序列
     *
     * @return
     */
    private static String getIncrement() {
        int tempSerialNo = 0;
        lock.lock();
        try {
            serialNo = serialNo >= 99 ? 0 : serialNo + 1;
            tempSerialNo = serialNo;
        } catch (Exception e) {
            LogUtil.error("tryLock throws Exception:", e);
            throw new RuntimeException("tryLock throws Exception!");
        } finally {
            lock.unlock();
        }
        return tempSerialNo < 10 ? "0" + tempSerialNo : "" + tempSerialNo;
    }

    /**
     * 返回两位随机整数
     * @return
     */
    private static String getRandomNum() {
        int num = new Random(System.nanoTime()).nextInt(100);
        return num < 10 ? "0" + num : num + "";
    }


    /**
     * 获取IP的最后两位数字
     */
    private static String getLastNumOfIP() {
        String ip = getCurrentIP();
        Integer i = ip.lastIndexOf(".");
        String lastIp = ip.substring(i + 1);
        if (lastIp.length() == 1) {
            lastIp = "0" + lastIp;
        } else if (lastIp.length() == 2) {

        } else if (lastIp.length() == 3) {
            lastIp = lastIp.substring(0, 2);
        }
        return lastIp;
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    private static String getCurrentIP() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LogUtil.error("getLocalHost throws UnknownHostException:", e);
            throw new RuntimeException("can not get ip!");
        }
        if (StringUtils.isBlank(ip)) {
            throw new RuntimeException("ip is blank!");
        }
        return ip;
    }
}
