package com.king.util.httpclient;

import com.king.util.enums.HttpErrorEnum;
import com.king.util.enums.HttpMethodEnum;
import com.king.util.exception.BusinessMsgException;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description: 定向连接池
 **/
public class HttpClientUtils {
    public static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder httpBulder        = null;
    private static RequestConfig requestConfig     = null;

    private static int MAXCONNECTION = 200;

    private static int DEFAULTMAXCONNECTION = 100;

    public static final String  PKCS12  = "PKCS12";

    public static int SOCKETTIMEOUT=20000;
    public static int CONNECTTIMEOUT=10000;
    public static int CONNECTIONREQUESTTIMEOUT=10000;
    static {
        requestConfig = RequestConfig.custom().setSocketTimeout(SOCKETTIMEOUT).
                setConnectTimeout(CONNECTTIMEOUT).
                setConnectionRequestTimeout(CONNECTIONREQUESTTIMEOUT).build();
        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAXCONNECTION);
        connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
        httpBulder = HttpClients.custom();
        httpBulder.setConnectionManager(connectionManager);
        httpBulder.setDefaultRequestConfig(requestConfig);
        httpBulder.setConnectionManagerShared(true);
    }

    public static void rebulid(int maxConnection,int defaultMaxConnection,int socketTimeout,int connectTimeout,int connectionRequestTimeout){
        if(SOCKETTIMEOUT==socketTimeout && CONNECTTIMEOUT==connectTimeout && CONNECTIONREQUESTTIMEOUT==connectionRequestTimeout){
        }else{
            requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).
                    setConnectTimeout(connectTimeout).
                    setConnectionRequestTimeout(connectionRequestTimeout).build();
        }
        if(MAXCONNECTION == maxConnection && DEFAULTMAXCONNECTION == defaultMaxConnection){

        }else{
            connectionManager.setMaxTotal(MAXCONNECTION);
            connectionManager.setDefaultMaxPerRoute(DEFAULTMAXCONNECTION);
        }
        httpBulder.setConnectionManager(connectionManager);
        httpBulder.setDefaultRequestConfig(requestConfig);
        httpBulder.setConnectionManagerShared(true);
    }
    public static SSLContext createIgnoreVerifySSL() {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("TLSv1");
            // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
            X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sc.init(null, new TrustManager[] { trustManager }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sc;
    }

    public static CloseableHttpClient getConnection() {
        CloseableHttpClient httpClient = httpBulder.setSSLContext(null).build();
        return httpClient;
    }

    public static CloseableHttpClient createSSLInsecureClient() {
        SSLContext ctx = createIgnoreVerifySSL();
        CloseableHttpClient httpClient = httpBulder.setSSLContext(ctx).build();
        return httpClient;
    }

    /**
     *
     * @param certFilePath 证书地址
     * @param keyPasswd  证书秘钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws IOException
     * @throws UnrecoverableKeyException
     * @throws KeyManagementException
     */
    public static CloseableHttpClient getHttpsClient(File certFilePath, String keyPasswd)
            throws NoSuchAlgorithmException, KeyStoreException, CertificateException, IOException,
            UnrecoverableKeyException, KeyManagementException {
        FileInputStream instream = new FileInputStream(certFilePath);
        KeyStore keyStore = KeyStore.getInstance(PKCS12);
        try {
            keyStore.load(instream, keyPasswd.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, keyPasswd.toCharArray()).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        return httpclient;
    }

    /**
     * @param map 数据参数
     * @param url 请求地址
     * @param method 方法
     * @return
     */
    private static HttpUriRequest getRequestMethod(Map<String, String> map, String url, String method) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        if (map != null) {
            Set<Map.Entry<String, String>> entrySet = map.entrySet();
            for (Map.Entry<String, String> e : entrySet) {
                String name = e.getKey();
                String value = e.getValue();
                NameValuePair pair = new BasicNameValuePair(name, value);
                params.add(pair);
            }
        }
        HttpUriRequest reqMethod = null;
        if (HttpMethodEnum.POST.getMethod().equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url).addParameters(params.toArray(new BasicNameValuePair[params.size()])).setConfig(requestConfig).build();
        } else if (HttpMethodEnum.GET.getMethod().equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url).addParameters(params.toArray(new BasicNameValuePair[params.size()])).setConfig(requestConfig).build();
        }
        return reqMethod;
    }

    /**
     *
     * @param entity  参数
     * @param url  请求地址
     * @param method  请求方式 post/get
     * @return
     */
    private static HttpUriRequest getRequestMethod(HttpEntity entity, String url, String method) {
        HttpUriRequest reqMethod = null;
        if (HttpMethodEnum.POST.getMethod().equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url).setEntity(entity).setConfig(requestConfig).build();
        } else if (HttpMethodEnum.GET.getMethod().equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url).setEntity(entity).setConfig(requestConfig).build();
        }
        return reqMethod;
    }

    /**
     * @param map 数据参数
     * @param url 请求地址
     * @param method 方法post/get
     * @param handle 回调处理类
     * @throws Exception
     */
    public static<T> T  execute(Map<String, String> map, String url, HttpMethodEnum method,
                                HttpEntityHandle<T> handle) throws Exception {
        return execute(map, url, method, handle, null);
    }

    /**
     *
     * @param map 数据参数
     * @param url 请求地址
     * @param method 方法post/get
     * @param handle 回调处理类
     * @param headers 请求头
     * @param <T>
     * @return
     * @throws Exception
     */
    public static<T> T execute(Map<String, String> map, String url, HttpMethodEnum method, HttpEntityHandle<T> handle,
                               Header[] headers) throws Exception {
        CloseableHttpClient client = null;
        if(url.toLowerCase().startsWith("https")){// 执行 Https 请求.
            client =createSSLInsecureClient();
        }else{// 执行 Http 请求.
            client = getConnection();
        }
        HttpUriRequest uri = getRequestMethod(map, url, method.getMethod());
        if (headers != null) {
            uri.setHeaders(headers);
        }
        HttpResponse response = client.execute(uri);
        if(response.getStatusLine().getStatusCode()== HttpStatus.SC_MOVED_TEMPORARILY){
            Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
            String newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
            return execute(map, newuri, method, handle, headers);
        }else if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            return handle.invoke(entity);
        } else {
            throw new BusinessMsgException(HttpErrorEnum.HTTPCLIENT_ERROR);
        }
    }

    /**
     * @param entity 请求参数
     * @param url 请求地址
     * @param method 方法post/get
     * @param handle 回调处理类
     * @throws Exception
     */
    public static<T> T  execute(HttpEntity entity, String url, HttpMethodEnum method,
                                HttpEntityHandle<T> handle) throws Exception {
        return execute(entity, url, method, handle, null);
    }

    /**
     *
     * @param entity 请求参数
     * @param url 请求地址
     * @param method 方法post/get
     * @param handle 回调处理类
     * @param headers 请求头
     * @param <T>
     * @return
     * @throws Exception
     */
    public static<T> T  execute(HttpEntity entity, String url, HttpMethodEnum method, HttpEntityHandle<T> handle,
                                Header[] headers) throws Exception {
        HttpClient client = getConnection();
        HttpUriRequest uri = getRequestMethod(entity, url, method.getMethod());
        if (headers != null) {
            uri.setHeaders(headers);
        }
        HttpResponse response = client.execute(uri);

        if(response.getStatusLine().getStatusCode()==HttpStatus.SC_MOVED_TEMPORARILY){
            Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
            String newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
            return execute(client,entity, newuri, method, handle, headers);
        }else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entityResult = response.getEntity();
            return handle.invoke(entityResult);
        } else {
            throw new BusinessMsgException(HttpErrorEnum.HTTPCLIENT_ERROR);
        }
    }

    /**
     *
     * @param client 自定义请求头
     * @param entity 请求参数
     * @param url 请求地址
     * @param method 方法post/get
     * @param handle 回调处理类
     * @param headers 请求头
     * @param <T>
     * @return
     * @throws Exception
     */
    public static<T> T  execute(HttpClient client, HttpEntity entity, String url, HttpMethodEnum method, HttpEntityHandle<T> handle,
                                Header[] headers) throws Exception {
        HttpUriRequest uri = getRequestMethod(entity, url, method.getMethod());
        if (headers != null) {
            uri.setHeaders(headers);
        }
        HttpResponse response = client.execute(uri);

        if(response.getStatusLine().getStatusCode()==HttpStatus.SC_MOVED_TEMPORARILY){
            Header header = response.getFirstHeader("location"); // 跳转的目标地址是在 HTTP-HEAD 中的
            String newuri = header.getValue(); // 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息是啥。
            return execute(client,entity, newuri, method, handle, headers);
        }else if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entityResult = response.getEntity();
            return handle.invoke(entityResult);
        } else {
            throw new BusinessMsgException(HttpErrorEnum.HTTPCLIENT_ERROR);
        }
    }
}
