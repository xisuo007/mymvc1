package com.king.hutoolHttp.httpclient;


import com.alibaba.fastjson.JSON;
import com.king.lomboktest.Car;
import com.king.util.enums.HttpMethodEnum;
import com.king.util.httpclient.HttpClientUtils;
import com.king.util.httpclient.HttpEntityHandle;
import org.apache.http.HttpEntity;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Date;

/**
 * @Author ljq
 * @Date 2019/12/26 10:24
 */
public class SimpleClient {
    public static void main(String[] args){
    }

    public void testKingCarHttpClient(){
        Car car = new Car().color("bule").name("audi");
        try {
            //组装请求路径
            URIBuilder builder = new URIBuilder("url");
            builder.addParameter("name","roy");
            URI uri = builder.build();

            //组装内容
            StringEntity entity = new StringEntity(JSON.toJSONString(car),"utf-8");
            entity.setContentType("application/json");

            //发送请求，并获取返回内容
            String resultString = HttpClientUtils.execute(entity, uri.toString(), HttpMethodEnum.GET, httpEntity -> EntityUtils.toString(httpEntity, "UTF-8"));
            System.out.println(resultString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test(){
        try {
            ContentBody cd = new InputStreamBody(new ByteArrayInputStream(new byte[1024]),"filename");
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addTextBody("name","roy")
                    .addTextBody("type","zip")
                    .addTextBody("date",new Date().toString())
                    .addPart("file",cd)
                    .build();
            Object execute = HttpClientUtils.execute(entity, "url", HttpMethodEnum.POST, new HttpEntityHandle() {

                @Override
                public Object invoke(HttpEntity entity) throws Exception {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            });
            System.out.println(execute.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
