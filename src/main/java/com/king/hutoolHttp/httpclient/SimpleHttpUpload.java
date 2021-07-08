package com.king.hutoolHttp.httpclient;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;

import java.io.File;

/**
 * Created by ljq on 2021-7-6 11:18
 */
public class SimpleHttpUpload {
    public static void main(String[] args){
        String targetURL = null;// TODO 指定URL
        File targetFile = null;// TODO 指定上传文件

        targetFile = new File("1.mp3");
        targetURL = "http://localhost:8080/test/tt"; //servleturl
        PostMethod filePost = new PostMethod(targetURL);

        try
        {

            //通过以下方法可以模拟页面参数提交
            //filePost.setParameter("name", "中文");
            //filePost.setParameter("pass", "1234");

            Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
            filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);
            if (status == HttpStatus.SC_OK)
            {
                System.out.println("上传成功");
                // 上传成功
            }
            else
            {
                System.out.println("上传失败");
                // 上传失败
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            filePost.releaseConnection();
        }
    }
}
