package com.king.poi.hutoolPoi;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ljq on 2019/3/25 11:07
 */
public class HutoolExcel {
    public static void main(String[] args){
        //new HutoolExcel().testWriterList();
        //new HutoolExcel().testWriterMap();
        new HutoolExcel().testWriterBean();
        //new HutoolExcel().testWriterDIY();
    }

    public void testWriterList(){
        try {
            List<String> row1 = CollUtil.newArrayList("aa","bb","cc","dd");
            List<String> row2 = CollUtil.newArrayList("aa2","bb2","cc2","dd2");
            List<String> row3 = CollUtil.newArrayList("aa3","bb3","cc3","dd3");
            List<String> row4 = CollUtil.newArrayList("aa4","bb4","cc4","dd4");

            List<List<String>> rows = CollUtil.newArrayList(row1,row2,row3,row4);
            //生成Excel
            ExcelWriter writer = ExcelUtil.getWriter("d:/writeTest.xlsx");
            //通过构造方法创建writer
            //ExcelWriter writer1 = new ExcelWriter("d:/writeTest1.xls");

            writer.passCurrentRow();//跳过当前行，即第一行，非必须
            writer.merge(rows.size()-1,"测试标题");
            writer.write(rows);//一次行写出内容
            writer.close();//写完关闭writer,释放内存
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void testWriterMap(){
        Map<String,Object> row1 = new LinkedHashMap<>();
        row1.put("姓名","张三");
        row1.put("年龄","23");
        row1.put("成绩","88.23");
        row1.put("是否合格",true);
        row1.put("考试日期", DateUtil.date());

        Map<String,Object> row2 = new LinkedHashMap<>();
        row1.put("姓名","李四");
        row1.put("成绩","85.23");
        row1.put("是否合格",true);
        row1.put("考试日期", DateUtil.date());

        ArrayList<Map<String,Object>> rows = CollUtil.newArrayList(row1,row1);

        ExcelWriter writer = ExcelUtil.getWriter("d:/writerMapTest.xlsx");
        writer.merge(row1.size()-1,"一班成绩");
        writer.write(rows);
        writer.close();
    }

    public void testWriterBean(){
        User bean1 = new User();
        bean1.setName("张三");
        bean1.setAge(22);
        bean1.setPass(true);
        bean1.setScore(66.30);
        bean1.setExamDate(DateUtil.date());

        User bean2 = new User();
        bean2.setName("李四");
        bean2.setAge(28);
        bean2.setPass(false);
        bean2.setScore(38.50);
        bean2.setExamDate(DateUtil.date());

        List<User> rows = CollUtil.newArrayList(bean1);
        rows.add(bean2);

        ExcelWriter writer = ExcelUtil.getWriter("d:/writerBeanText.xlsx");

        //可以自定义别名  可以按照别名的顺序来排序
        writer.addHeaderAlias("name","姓名");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("score", "分数");
        writer.addHeaderAlias("isPass", "是否通过");
        writer.addHeaderAlias("examDate", "考试时间");

        writer.merge(4,"一班成绩");
        writer.setCurrentRow(1);
        writer.write(rows);
        writer.close();
    }

    /**
     * 写出到客户端下载
     */
    public void testwriterServlet(HttpServletResponse response){
        User bean1 = new User();
        bean1.setName("张三");
        bean1.setAge(22);
        bean1.setPass(true);
        bean1.setScore(66.30);
        bean1.setExamDate(DateUtil.date());

        User bean2 = new User();
        bean2.setName("李四");
        bean2.setAge(28);
        bean2.setPass(false);
        bean2.setScore(38.50);
        bean2.setExamDate(DateUtil.date());

        List<User> rows = CollUtil.newArrayList(bean1);
        rows.add(bean2);

        ExcelWriter writer = ExcelUtil.getWriter();

        writer.write(rows);
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename=TestLombok.xls");//test为下载时文件名
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.flush(out);
        writer.close();
    }

    public void testWriterDIY(){
        User bean1 = new User();
        bean1.setName("张三");
        bean1.setAge(22);
        bean1.setPass(true);
        bean1.setScore(66.30);
        bean1.setExamDate(DateUtil.date());

        User bean2 = new User();
        bean2.setName("李四");
        bean2.setAge(28);
        bean2.setPass(false);
        bean2.setScore(38.50);
        bean2.setExamDate(DateUtil.date());

        List<User> rows = CollUtil.newArrayList(bean1);
        rows.add(bean2);

        ExcelWriter writer = ExcelUtil.getWriter("d:/writerDIYText.xlsx");

        //设置单元格背景
        StyleSet style = writer.getStyleSet();//定义单元格背景色
        style.setBackgroundColor(IndexedColors.RED,false);//第二个参数表示是否也设置头部单元格背景

        //设置自定义字体
        Font font = writer.createFont();
        font.setBold(true);//设置是否加粗
        font.setColor(Font.COLOR_RED);//设置字体颜色
        font.setItalic(true);//是否用斜体
        /*
        *   setFamily() 设置字体类型  如宋体 楷体微软雅黑  Consolas 等
            setStyleName()  "Normal" 正常  "Bold" 加粗  "Italic" 斜体  "Bold Italic" 加粗斜体   这个函数的设置还有待验证
            setPointSize() 设置点大小
            setPixelSize() 设置像素大小
            setWeight() 设置粗细
            setBold(bool) 设置加粗
            setStyle(Style style); 设置字体风格 待验证
            setItalic(bool b); 设置斜体
            setUnderline(bool); 设置下划线
            1setOverline(bool); 设置上划线
        */

        //设置多个sheet
        //初始化时定义表名
        ExcelWriter writer2 = new ExcelWriter("d:/aaa.xls", "表1");
        //切换sheet，此时从第0行开始写
        writer.setSheet("表2");
        writer.setSheet("表3");

        writer.getStyleSet().setFont(font,true);//第二个参数表示是否忽略头部样式
    }

    public void testWriterBigDate(){
        List<?> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765);
        List<?> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1", DateUtil.date(), 250.7676);
        List<?> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2", DateUtil.date(), 0.111);
        List<?> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3", DateUtil.date(), 35);
        List<?> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4", DateUtil.date(), 28.00);

        List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

        BigExcelWriter writer= ExcelUtil.getBigWriter("e:/xxx.xlsx");
        // 一次性写出内容，使用默认样式
        writer.write(rows);
        // 关闭writer，释放内存
        writer.close();
    }

}
