package com.king.hutoolHttp.httpclient;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * 文件上传
 * Created by ljq on 2021-7-6 9:46
 */
public class NativeDownHttp {

    /**
     * 通过httpClient上传文件
     * @param fileName
     * @param path
     * @return 外部域名的下载url
     */
    public static String UploadFileByHttpClient(String fileName, String path, String url, ContentBody cd) {
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            //HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
            MultipartEntityBuilder builder = (MultipartEntityBuilder) MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532)
                    .addTextBody("source", "us").addPart("file", cd).build();

            httpPost.addHeader("header1", "111");//头部放文件上传的head可自定义
            File file = new File(path+fileName); //上传文件的路径
            builder.addBinaryBody("file", file, ContentType.MULTIPART_FORM_DATA, fileName);
            builder.addTextBody("params1", "1");//其余参数，可自定义
            builder.addTextBody("params2", "2");

            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();//接收调用外部接口返回的内容
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                // 返回的内容都在content中
                InputStream content = responseEntity.getContent();
                // 定义BufferedReader输入流来读取URL的响应
                BufferedReader in = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
                if(!StringUtils.isBlank(result)) {
                    System.out.println("上传文件"+fileName+"返回参数==>"+result);
                }else {
                    System.out.println("上传文件失败：返回result为null");
                }

            }
        }catch(Exception e) {
            System.out.println("上传文件失败：");
            e.printStackTrace();
        }finally {//处理结束后关闭httpclient的链接
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}
