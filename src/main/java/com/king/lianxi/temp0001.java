package com.king.lianxi;


import com.alibaba.fastjson.JSON;
import com.king.cron.Test01;
import com.king.demo.leetcode.test1;
import com.king.java8.lambda.User;
import com.king.util.DateUtil;
import com.king.util.OrderGenerater;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.apache.poi.ss.formula.functions.T;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @Author ljq
 * @Date 2019/12/27 17:44
 */
public class temp0001 {

    public static void main(String[] args) throws URISyntaxException {
        HashMap<String,String> map = new HashMap<>();
        map.put(null,"qqq");
        map.put(null,"222");
        map.put("111","11111");
        System.out.println(map.get("111"));
        System.out.println(map.get(null));
        System.out.println(map.entrySet().size());

        Car car = new Car("11", null, true);
        System.out.println(car.getIsA().toString());


        boolean b = DateUtil.compareDate(DateUtil.parseStdDate("2020-03-17 13:10:10", DateUtil.DateMode_4), new Date(), 0);
        System.out.println(b);

        Car2 car2 = new Car2("ceshi",car);
        System.out.println(JSON.toJSONString(car2));

        URIBuilder builder = new URIBuilder("www.baidu.com");
        builder.addParameter("date",DateUtil.datetime_format.format(new Date()));
        //builder.addParameter("aaa","code");
        //builder.addParameter("content",JSON.toJSONString(car));
        System.out.println(JSON.toJSONString(car));
        System.out.println(builder.build().toString());

        test002("");

        String s = OrderGenerater.generateOrderNo();
        System.out.println(s);

        String format = DateUtil.datetime_format.format(new Date());
        System.out.println(format);

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class Car{
        private String name;
        private Integer age;
        private Boolean isA;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class Car2<T>{
        private String code;
        private T content;
    }

    private static void test002(Object t){
        Car2 car2 = new Car2("0000",t);
        System.out.println(JSON.toJSONString(car2));
    }

}
