package com.king.lianxi;

import com.king.util.log.LogUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RowKeyGenerator {
    private volatile static int serialNo = 0;
    private static final String FORMAT_STRING = "HHmmssSSS";
    /**
     * 使用公平锁防止饥饿
     */
    private static final Lock lock = new ReentrantLock(true);

    /**
     * 生成规则 时分秒毫秒+2位随机数+两位自增序列
     * 采用可重入锁减小锁持有的粒度，提高系统在高并发情况下的性能
     *
     * @return
     */
    public static String generateOrderNo() {
        StringBuilder builder = new StringBuilder();
        builder.append(getDateTime(FORMAT_STRING));
        builder.append(getRandomNum()).append(getIncrement());
        return builder.toString();
    }

    private static String getDateTime(String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(new Date());
    }

    private static String getRandomNum() {
        int num = new Random(System.nanoTime()).nextInt(100);
        if (num < 10) {
            return "0" + num;
        } else {
            return num + "";
        }
    }

    /**
     * 获取自增序列
     */
    private static String getIncrement() {
        int tempSerialNo = 0;
        lock.lock();
        try {
            if (serialNo >= 99) {

                serialNo = 0;
            } else {
                serialNo = serialNo + 1;
            }
            tempSerialNo = serialNo;
        } catch (Exception e) {
            LogUtil.error("tryLock throws Exception:", e);
            throw new RuntimeException("tryLock throws Exception!");
        } finally {
            lock.unlock();
        }
        if (tempSerialNo < 10) {
            return "0" + tempSerialNo;
        } else {
            return "" + tempSerialNo;
        }
    }
}