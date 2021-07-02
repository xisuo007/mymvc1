package com.king.lianxi;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ljq on 2020/6/2 16:57
 */
public class test20200602 {

    public static void main(String[] args) throws Exception{
        Test01 test01 = new Test01();
        test01.setId(1);
        test01.setAge(new BigDecimal(8));
        test01.setAge2(new BigDecimal(10000));
        test01.setEnd(new Date());


        List<Test02> lists = Arrays.asList(new Test02(1,"苹果手机"),
                new Test02(2,"华为手机"),
                new Test02(3,"联想笔记本"),
                new Test02(4,"机械键盘"),
                new Test02(5,"雷蛇鼠标"));

        Map<Integer, List<Test02>> collect = lists.stream().collect(Collectors.groupingBy(e -> e.getId()));
        List<Test02> test02s = collect.get(6);
        System.out.println(test02s);


        //int i = new BigDecimal(100).compareTo(new BigDecimal(1000));
        //System.out.println(i);

        String str = "liu,li";
        String replace = str.replace(",,", ",");
        System.out.println(str);
        System.out.println(str.replace(",,",",").toUpperCase());


        BigDecimal multiply = new BigDecimal(0.67).divide(new BigDecimal(100)).multiply(new BigDecimal(120000));
        System.out.println(multiply);
        System.out.println(multiply.toString());

        //
        //BigDecimal divide1 = new BigDecimal(10).divide(new BigDecimal(4000).divide(new BigDecimal(100), 2, BigDecimal.ROUND_UP), 2, BigDecimal.ROUND_UP);
        //System.out.println(divide1);
        //
        BigDecimal a = new BigDecimal("85800");
        //BigDecimal divide = a.divide(new BigDecimal(110000), 2, BigDecimal.ROUND_UP);
        //System.out.println(divide);


        //将application/x-www-form-urlencoded字符串转换成普通字符串
        //采用UTF-8字符集进行解码
        System.out.println(URLDecoder.decode("%E5%8C%97%E4%BA%AC%E5%A4%A7%E5%AD%A6", "UTF-8"));
        //采用GBK字符集进行解码
        System.out.println(URLDecoder.decode("%B1%B1%BE%A9%B4%F3%D1%A7", "GBK"));

        // 将普通字符串转换成application/x-www-form-urlencoded字符串
        //采用utf-8字符集
        System.out.println(URLEncoder.encode("北京大学", "UTF-8"));
        //采用GBK字符集
        System.out.println(URLEncoder.encode("北京大学", "GBK"));





    }
    @Data
    static
    class Test01{
        private Integer id;
        private BigDecimal age;
        private BigDecimal age2;
        private Date end;
    }

    @Data
    @AllArgsConstructor
    static
    class Test02{
        private Integer id;
        private String end;
    }

}
