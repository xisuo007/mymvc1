package com.king.util.iputil;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ip获取工具类
 */
public class IpUtil {

    private static final IpUtil INSTANCE = new IpUtil();

    private IpUtil() {}

    public static IpUtil getInstance() {
        return INSTANCE;
    }

    /**
     * 获取网络请求ip地址
     * @param request 请求
     * @return ip地址
     */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取本机IP
     *
     * @return
     */
    public String getCurrentIP() {
        String ip;
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("can not get ip!");
        }
        if (StringUtils.isBlank(ip)) {
            throw new RuntimeException("ip is blank!");
        }
        return ip;
    }

    /**
     * 获取IP的最后两位数字
     *
     * @return
     */
    public String getLastNumOfIP() {
        String ip = getCurrentIP();
        Integer i = ip.lastIndexOf(".");
        String lastIp = ip.substring(i + 1);
        if (lastIp.length() == 1) {
            lastIp = "00" + lastIp;
        } else if (lastIp.length() == 2) {
            lastIp = "0"+lastIp.substring(0, 2);
        } else if (lastIp.length() == 3) {
            lastIp = lastIp.substring(0, 2);
        }
        return lastIp;
    }

}
