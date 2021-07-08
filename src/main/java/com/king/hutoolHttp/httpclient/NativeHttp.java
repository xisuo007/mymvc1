package com.king.hutoolHttp.httpclient;

import com.king.util.StringTools;
import com.king.util.log.LogUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 下载文件
 * Created by ljq on 2021-7-6 9:46
 */
public class NativeHttp {

    private byte[] validated(String url) {
        // 验证结果
        byte[] resultDTO = new byte[1024];
        try {
            if (StringTools.isEmpty(url)) {
                System.out.println("url为空！！！");
                return resultDTO;
            }
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            conn.setRequestMethod("GET");
            byte[] retBuf = null;
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 流转成字节
                try (BufferedInputStream bis = new BufferedInputStream(conn.getInputStream())){
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    byte[] buf = new byte[2048];
                    int count = 0;
                    while ((count = bis.read(buf,0,2048))!=-1){
                        out.write(buf);
                    }
                    out.flush();
                    retBuf = out.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("通过url获取数据失败！！！");
                return resultDTO;
            }
            resultDTO = retBuf;
        } catch (Exception ex) {
            LogUtil.error("通过url获取数据出现异常！！！", ex);
            System.out.println("通过url获取数据出现异常！！！");
            return resultDTO;
        }
        return resultDTO;
    }
}
