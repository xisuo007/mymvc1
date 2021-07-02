package com.king.hutoolHttp;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.StreamProgress;
import cn.hutool.core.util.ReUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by ljq on 2019/3/25 15:12
 */
public class HttpTest {
    public void getUrl(){

        //请求不区分http或https，可以自动通过header信息判断编码，也可以自定义编码
        String get = HttpUtil.get("URL");//get请求

        String get1 = HttpUtil.get("https://www.baidu.com", Charset.forName("UTF-8"));

        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("city","杭州");

        //可单独传入http参数，自动拼接
        String get2 = HttpUtil.get("https://www.baidu.com", paramMap);
    }

    public void postUrl(){
        HashMap<String,Object> paramMap = new HashMap<>();
        paramMap.put("city","杭州");
        //post请求，先用map把form表单数据填充好
        String post = HttpUtil.post("URL", paramMap);

        //文件上传只需讲参数中的键指定（默认file），值设为文件对象即可，文件上传与表单提交区别不大
        paramMap.put("file", FileUtil.file("d:\\22222.jpg"));
        String post1 = HttpUtil.post("https://www.baidu.com", paramMap);
    }

    /**
     * httputil对文件下载做了封装，文件下载时采用流的方式读写，内存中只保留一部分缓存，然后分块写入到硬盘，大文件对内存压力不会太大
     */
    public void download(){
        String fileUrl = "http://files.kingcars.cn/group1/M00/01/21/rBAGVFyGGluAQh-xAABMAO_qg_E197.xls";
        long size = HttpUtil.downloadFile(fileUrl, FileUtil.file("d:/"));
        System.out.println("Download size: "+size);

        //-----------------------------------------------------------
        //如果想要感知下载进度，需要调用重载方法
        //带进度显示的文件下载
        HttpUtil.downloadFile(fileUrl, FileUtil.file("d:/"), new StreamProgress() {
            @Override
            public void start() {
                System.out.println("开始下载。。。。。。。");
            }

            @Override
            public void progress(long progressSize) {
                System.out.println("已经下载："+ FileUtil.readableFileSize(progressSize));
            }

            @Override
            public void finish() {
                System.out.println("下载完成！");
            }
        });
    }


    public void otherMethod(HttpServletRequest request){
        //根据指定Http头信息获取客户端ip地址，此方法适用于再Nginx转发时获取真实客户端地址的快捷方法（此方法依赖与Servlet-api）
        HttpUtil.getClientIP(request,"");
        /**
        HttpUtil.encode和HttpUtil.decode 两个方法封装了JDK的URLEncoder.encode和URLDecoder.decode方法，可以方便的对URL参数进行URL编码和解码。
        HttpUtil.toParams和HttpUtil.decodeParams 两个方法是将Map参数转为URL参数字符串和将URL参数字符串转为Map对象
        HttpUtil.urlWithForm是将URL字符串和Map参数拼接为GET请求所用的完整字符串使用
        HttpUtil.getMimeType 根据文件扩展名快速获取其MimeType（参数也可以是完整文件路径）
         */
    }

    /**
     * 通过链式构建请求，方便设置http头信息和表单信息，最后调用execute方法执行请求，返回HttpResponse对象，其包含了服务器响应的一些信息，
     * 包括响应的内容和响应头信息，调用body方法即可获取内容。
     */
    public void httpRequest(){
        //链式构建请求
        String body = HttpRequest.post("url")
                //头信息多个可以重复调用
                .header(Header.USER_AGENT, "TestLombok http")
                .header("name","toy")
                //表单内容
                .form(new HashMap<>())
                //超时 毫秒
                .timeout(20000)
                .execute().body();
        System.out.println(body);

        //-----------------------------------------------------------
        //Restful请求
        String json = "{\"age\": 15}";
        String body1 = HttpRequest.post("url").body(json).execute().body();
        System.out.println(body1);

        //-----------------------------------------------------------
        /**  其他可设置的操作
         指定请求头
         自定义Cookie（cookie方法）
         指定是否keepAlive（keepAlive方法）
         指定表单内容（form方法）
         指定请求内容，比如rest请求指定JSON请求体（body方法）此时不能用from方法
         超时设置（timeout方法）
         指定代理（setProxy方法）
         指定SSL协议（setSSLProtocol）
         简单验证（basicAuth方法）
         */
    }

    /**
     Http状态码（getStatus方法）
     返回内容编码（contentEncoding方法）
     是否Gzip内容（isGzip方法）
     返回内容（body、bodyBytes、bodyStream方法）
     响应头信息（header方法）
     */
    public void httpResponse(){
        HttpResponse execute = HttpRequest.post("url").execute();
        System.out.println(execute);
        String body = execute.body();

        //预定义的头信息
        execute.header(Header.CONTENT_ENCODING);//获取头信息编码格式等
        execute.header("Content-Disposition");//获取自定义头信息
    }

    /**
     * 测试爬取oschina的资讯
     */
    public void testHttpUtil(){
        String listContent = HttpUtil.get("https://www.oschina.net/news/widgets/_news_index_all_list?p=2&type=ajax");
        //使用正则获取所有标题
        List<String> all = ReUtil.findAll("title=\"(.*?)\">", listContent, 1);
        for (String s : all) {
            System.out.println(s);
        }

        /**
         * Received fatal alert: handshake_failure 错误
         * 方法1：如果是jdk8，升级成最新版本1.8.0_181
         * 方法2：尝试添加如下代码  System.setProperty("https.protocols","TLSv1.2,TLSv1.1,SSLv3");
         */
    }

    /**
     * 传入两个http文件路径，对比首行是否相同
     * @param templatePath
     * @param handlePath
     * @return
     */
    public boolean testExcel(String templatePath,String handlePath){
        InputStream targetIn = null;
        InputStream in = null;
        try {
            Assert.notNull(templatePath,"模板文件路径不能为空");
            Assert.notNull(handlePath,"处理文件路径不能为空");
            String templateType = templatePath.substring(templatePath.lastIndexOf(".")+1);
            String handleType = handlePath.substring(handlePath.lastIndexOf(".")+1);
            Assert.notNull(templateType,"模板文件类型无法判断");
            Assert.notNull(handleType,"处理文件类型无法判断");
            if (!templateType.equals(handleType)) {
                throw new RuntimeException("文件类型不匹配");
            }

            targetIn = new URL(handlePath).openStream();
            in = new URL(templatePath).openStream();
            Workbook wb = new XSSFWorkbook(in);
            Workbook targetWb = new XSSFWorkbook(targetIn);
            Sheet sheet = wb.getSheetAt(0);
            Sheet targetSheet = targetWb.getSheetAt(0);
            //获取第一行进行对比
            Row firstRow = sheet.getRow(0);
            Row handleFirstRow = targetSheet.getRow(0);

            int indexMin = firstRow.getFirstCellNum();
            int indexMax = firstRow.getLastCellNum();
            int indexMax2 = handleFirstRow.getLastCellNum();
            if (indexMax != indexMax2) {
                throw new RuntimeException("第一行列数不一致");
            }
            if (indexMax <= 0 || indexMax2 <= 0) {
                return false;
            }
            for (int i = indexMin; i < indexMax; i++) {
                Cell cell = firstRow.getCell(i);
                Cell cell2 = handleFirstRow.getCell(i);
                if (cell == null || cell2 == null){
                    throw new RuntimeException("有表头元素为空:"+cell.toString()+"--"+cell2.toString());
                }
                if (!cell.toString().equals(cell2.toString())) {
                    throw new RuntimeException("表头字段不一致：" + cell.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                targetIn.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static void main(String[] args){
        //new HttpTest().testHttpUtil();
        //boolean b = new HttpTest().testExcel("https://files.kingcars.cn/group1/M00/1A/8A/rBAGVF34MmCAfZXqAAAijxhePXs60.xlsx", "https://files.kingcars.cn/group1/M00/1A/8A/rBAGVF34MmCAfZXqAAAijxhePXs60.xlsx");
        //System.out.println(b);
        Map<String,Object> map = new HashMap<>();
        map.put("isSex","man");
        map.put("isAge","19");
        String body = HttpRequest.post("http://127.0.0.1:8103/orderInfoCar/test")
                .header(Header.USER_AGENT, "TestLombok http")
                .header("name", "toy")
                .cookie("testcookie")
                .form(map)
                //.body("{\"age\": 15}")    如果是用restful风格可以传json，否则可以用表单提交，同时用表单提交不生效
                .execute().body();

        System.out.println(body);
    }

    public Object test0(HttpServletRequest request, Object object){

        //获取表单内容
        Map<String,String> map = new HashMap<>();
        Map<String, String[]> stringMap = request.getParameterMap();
        Set<String> keys = stringMap.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            map.put(next,stringMap.get(next)[0]);
        }
        //parmMap========{isSex=man, isAge=19}
        System.out.println("parmMap========"+map.toString());

        //toy
        String name = request.getHeader("name");
        //testcookie
        Cookie[] cookies = request.getCookies();
        String contextPath = request.getContextPath();

        //获取单个表单value     man
        String isSex = request.getParameter("isSex");
        System.out.println(isSex);

        //获取body里的内容     rest请求body(json)
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            br = request.getReader();
            String str;
            if ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //{"age": 15}
        System.out.println(sb.toString());

        return "";
    }
}
