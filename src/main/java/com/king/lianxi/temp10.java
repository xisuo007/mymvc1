package com.king.lianxi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.king.controller.UserRet;
import com.king.thread.Test;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ljq on 2020/5/15 13:14
 */
public class temp10 {

    public static void main(String[] args) {
        //BigDecimal a = new BigDecimal(22.2);
        //System.out.println(a);
        //BigDecimal multiply = a.multiply(new BigDecimal(3));
        //System.out.println(multiply);


        //List<Test001> lists = Arrays.asList(new Test001(1, "苹果手机", new BigDecimal(5888.88), "手机"),
        //        new Test001(2, "华为手机", new BigDecimal(6899.33), "手机"),
        //        new Test001(3, "联想笔记本", new BigDecimal(9899.33), "电脑"),
        //        new Test001(4, "机械键盘", new BigDecimal(499.33), "键盘"),
        //        new Test001(5, "雷蛇鼠标", new BigDecimal(222.22), "鼠标"));
        //Test001 test001 = lists.stream().max((x, y) -> x.getPrice().compareTo(y.getPrice())).get();
        //Test001 test002 = lists.stream().min((x, y) -> x.getPrice().compareTo(y.getPrice())).get();
        //System.out.println(test001);
        //System.out.println(test002);


        //List<Test001> lists2 = Arrays.asList(new Test001(1, "苹果手机","手机"),
        //        new Test001(2, "华为手机","手机"),
        //        new Test001(3, "联想笔记本","电脑"),
        //        new Test001(4, "机械键盘","键盘"),
        //        new Test001(5, "雷蛇鼠标", "鼠标"));
        //System.out.println(lists2.get(1).getPrice());

        //Test001 test0011 = lists2.stream().filter(e->e.getPrice() != null).max((x, y) -> x.getPrice().compareTo(y.getPrice())).get();
        //System.out.println(test0011);

        Test001 test001 = new Test001();
        test001.setDirName("111").setId(1).setPrice(new BigDecimal(9.01)).setNAME("8989");
        System.out.println(JSON.toJSONString(test001));

    }
}

@Accessors(chain = true)
@Data
class Test001 {
    public int id;
    @JSONField(name = "NAME")
    public String NAME;
    public BigDecimal price;
    public String dirName;

    //public Test001(int id, String name, BigDecimal price, String dirName) {
    //    this.id = id;
    //    this.name = name;
    //    this.price = price;
    //    this.dirName = dirName;
    //}
    //
    //public Test001(int id, String name, String dirName) {
    //    this.id = id;
    //    this.name = name;
    //    this.dirName = dirName;
    //}
}
