package com.king.hutoolHttp.httpclient;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    /**
     * 发送http请求
     * @param url 请求路径
     * @param param 请求参数
     * @param headers 请求头
     * @param method 请求方法
     * @param timeOut 请求超时时间
     * @return 响应请求的返回结果
     * @throws Exception
     */
    public static String sendHttp(String url, String param, Map<String, String> headers, String method, Integer timeOut) throws Exception {
        System.out.println(headers);
        method = method == null ? "GET" : method;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";

        if (!StringUtils.isEmpty(param) && "GET".equalsIgnoreCase(method)) {
            url = url + "?" + param;
        }
        URL realUrl = new URL(url);
        URLConnection conn = realUrl.openConnection();
        if (timeOut != null && timeOut > 0) {
            conn.setReadTimeout(timeOut * 1000);
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) conn;
        httpURLConnection.setRequestMethod(method.toUpperCase());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpURLConnection.setRequestProperty(header.getKey(), header.getValue());
        }
        conn.setDoInput(true);
        if (!StringUtils.isEmpty(param) && "POST".equalsIgnoreCase(method)) {
            conn.setDoOutput(true);
            out = new PrintWriter(conn.getOutputStream());

            out.print(param);
            out.flush();
        }

        httpURLConnection.connect();
        in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result += line;
        }
        return result;
    }

    /**
     * 通过key=value的方式创建http的请求头
     * @param keyAndValues
     * @return
     */
    public static Map<String, String> createHearder(String... keyAndValues){
        if (keyAndValues == null || keyAndValues.length == 0){
            return new HashMap<String, String>();
        }
        Map<String, String> headers = new HashMap<String, String>();
        String[] split;
        for (String str : keyAndValues){

            split = str.split("=");
            if (split.length != 2){
                continue;
            }
            headers.put(split[0].trim(), split[1].trim());
        }
        return headers;
    }
}