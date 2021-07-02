package com.king.poi.img;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.king.util.enums.HttpMethodEnum;
import com.king.util.httpclient.HttpClientUtils;
import com.king.util.httpclient.HttpEntityHandle;
import com.king.util.log.LogUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.util.TempFile;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ljq on 2020-11-16 13:19
 */
public class ToPng {

    public static void main(String[] args) throws InterruptedException {

        //1.直接读取pdf文件为图片
        //pdf2png("D:\\temp","404","jpg");

        //2.直接读取url为文件，然后再转成图片
        //https://files.kingcars.cn/test/6f8dae13005f41fab97eaf7aca2e066e.pdf   大pdf测试
        String s = pdf2pngOne("https://files.kingcars.cn/us/4103df130ba74385be63bd32241ee44c.pdf", "testJpg", "jpg");
        //String s = pdf2pngOne("https://files.kingcars.cn/test/6f8dae13005f41fab97eaf7aca2e066e.pdf", "testJpg", "jpg");
        System.out.println(s);
        Thread.sleep(5000);

        //List<ContractData> list = new ArrayList<>();
        //
        //list.add(new ContractData(2001,	"https://files.kingcars.cn/us/5b6f7b8ac97d44db85ecbbf35230618a.pdf",""));
        //
        //for (ContractData data : list) {
        //    String s = pdf2pngOne(data.getUrl(), "testJpg", "jpg");
        //    data.setJpgPath(s);
        //}
        //FileWriter file = null;
        //StringBuffer str = new StringBuffer("");
        //for (ContractData data : list) {
        //    str.append(data.toString()).append("\n");
        //}
        //try {
        //    file = new FileWriter("d:\\jpgData4.txt");
        //    file.write(str.toString());
        //    file.flush();
        //    file.close();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }


    /**
     * 转换全部的pdf为一张图片
     * @param pdfUrl pdf下载地址
     * @param filename PDF文件名
     * @param type 图片类型
     */
    public static String pdf2pngOne(String pdfUrl,String filename,String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        String url = pdfUrl;
        String uploadPath = "https://fms.kingcars.cn//file/uploadFile";
        FileOutputStream out = null;
        InputStream in = null;
        File file = null;
        File outFile = null;
        PDDocument doc = null;
        InputStream jpgIn = null;
        String fileUrl = "";
        BufferedImage imageResult = null;
        try {
            //下载pdf
            file = File.createTempFile("temp00", ".pdf",new File(""));
            out = new FileOutputStream(file);
            HttpURLConnection uc = (HttpURLConnection) new URL(url).openConnection();
            uc.setDoInput(true);
            uc.connect();
            in = uc.getInputStream();
            byte[] buffer = new byte[4 * 1024];
            int byteRead = -1;
            while ((byteRead = (in.read(buffer))) != -1) {
                out.write(buffer, 0, byteRead);
            }
            out.flush();
            //合并图片
            doc = PDDocument.load(file);

            PDFRenderer renderer = new PDFRenderer(doc);
            int picNum = doc.getNumberOfPages();//pdf张数=图片数量

            int height = 0, // 总高度
                    width = 0, // 总宽度
                    _height = 0, // 临时的高度 , 或保存偏移高度
                    __height = 0; // 临时的高度，主要保存每个高度
            int[] heightArray = new int[picNum]; // 保存每个文件的高度
            BufferedImage picBuffer = null; // 保存图片流

            List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
            int[] _imgRGB; // 保存一张图片中的RGB数据
            if (picNum >=2) {
                for (int i = 0; i < picNum; i++) {
                    picBuffer = renderer.renderImageWithDPI(i,144);
                    int min = picBuffer.getWidth();
                    if (i == 0) {
                        width = min;
                    }else {
                        width = width>min?min:width;
                    }
                }
            }else {
                width = renderer.renderImageWithDPI(0,144).getWidth();
            }
            for (int i = 0; i < picNum; i++) {
                picBuffer = renderer.renderImageWithDPI(i, 144);
                heightArray[i] = _height = picBuffer.getHeight();// 图片高度
                height += _height; // 获取总高度
                _imgRGB = new int[width * _height];// 从图片中读取RGB
                _imgRGB = picBuffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
                imgRGB.add(_imgRGB);
            }
            _height = 0; // 设置偏移高度为0
            // 生成新图片
            imageResult = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < picNum; i++) {
                __height = heightArray[i];
                if (i != 0) {_height += __height;} // 计算偏移高度
                imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0, width); // 写入流中
            }
            outFile = File.createTempFile(filename,"."+type,new File(""));
            ImageIO.write(imageResult, "png", outFile);// 写图片


            //上传图片
            jpgIn = new FileInputStream(outFile);
            ContentBody cd = new InputStreamBody(jpgIn,filename+"."+type);
            HttpEntity entity = MultipartEntityBuilder.create().addTextBody("source", "app")
                    .addPart("file", cd).build();
            Object execute = HttpClientUtils.execute(entity, uploadPath, HttpMethodEnum.POST, new HttpEntityHandle() {
                @Override
                public Object invoke(HttpEntity entity) throws Exception {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            });
            JSONObject jsonObject = JSON.parseObject(execute.toString());
            String success = jsonObject.getString("success");
            if (StringUtils.isEmpty(success) || Objects.equals("false",success)) {
                LogUtil.error("文件上传服务器失败:",execute.toString());
                return "文件上传服务器失败";
            }
            String result = jsonObject.getString("result");
            JSONObject resultObject = JSON.parseObject(result);
            fileUrl = resultObject.getString("fileUrl");
            imageResult.flush();
            imageResult = null;
        } catch (Exception e) {
            e.printStackTrace();
            imageResult.flush();
            imageResult = null;
            return "转换错误！";
        }finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                doc.close();
                jpgIn.close();
                if (outFile != null) {
                    outFile.delete();
                }
                if (file != null) {
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return fileUrl;
    }

    /**
     * 转换全部的pdf
     * @param fileAddress 文件地址
     * @param filename PDF文件名
     * @param type 图片类型
     */
    public static void pdf2png(String fileAddress,String filename,String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(fileAddress+"\\"+filename+".pdf");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                ImageIO.write(image, type, new File(fileAddress+"\\"+filename+"_"+(i+1)+"."+type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *自由确定起始页和终止页
     * @param fileAddress 文件地址
     * @param filename pdf文件名
     * @param indexOfStart 开始页  开始转换的页码，从0开始
     * @param indexOfEnd 结束页  停止转换的页码，-1为全部
     * @param type 图片类型
     */
    public static void pdf2png(String fileAddress,String filename,int indexOfStart,int indexOfEnd,String type) {
        // 将pdf装图片 并且自定义图片得格式大小
        File file = new File(fileAddress+"\\"+filename+".pdf");
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = indexOfStart; i < indexOfEnd; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144); // Windows native DPI
                // BufferedImage srcImage = resize(image, 240, 240);//产生缩略图
                ImageIO.write(image, type, new File(fileAddress+"\\"+filename+"_"+(i+1)+"."+type));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    @AllArgsConstructor
    public static class ContractData{
        private int id;
        private String url;
        private String jpgPath;
    }

}
