package com.king.java8.assert0;

import com.king.controller.UserRet;
import com.king.util.DateUtil;
import org.springframework.util.Assert;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ljq on 2019/7/18 16:51
 * 断言一般用于调试代码，同时也可以用作参数校验
 */
public class AssertTest {
    public static void main(String[] args){
        AssertTest test = new AssertTest();
        //test.test00();
        //test.test01();
        //test.test02();
        //String currentDateTime = DateUtil.formateDate(DateUtil.addHourFroDate(new Date(), -1), "yyyy-MM-dd HH");
        //System.out.println(currentDateTime);

    }

    private void test00(){
        String str = "test";
        String str0 = null;
        String str1 = "";
        UserRet ret = new UserRet();

        Assert.notNull(ret,"对象不能为空");

        Integer integer = 11;
        List<String> list = Arrays.asList("1212","23432");
        List<String> nullList = new ArrayList<>();
        Assert.notNull(str,"字符串为空");
        Assert.notEmpty(list,"数组不能为空");

        //判断字符串是否为空
        //Assert.hasText(str1,"str不能为空");
        //Assert.notNull(str0,"字符串为空");
        Assert.notEmpty(nullList,"数组不能为空");

        Objects.requireNonNull(str1,"objects 验证字符串不能为空null");
    }


    private void test01(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7);
        List<Integer> collect = list.stream().map(e -> {
            if (e > 3) {
                return null;
            }
            return e;
        }).filter(f->f!=null).collect(Collectors.toList());
        System.out.println(collect);
    }

    private void test02(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        SimpleDateFormat data = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateTime = data.format(time);
        System.out.println(currentDateTime);

        String dateTime = DateUtil.formateDate(DateUtil.addDayFroDate(new Date(), -1), DateUtil.DateMode_1);
        System.out.println(dateTime);
    }



}
