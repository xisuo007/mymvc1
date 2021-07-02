package com.king.aop;

import com.king.aop.annotation.TestAnnotationAop;
import com.king.aop.annotation.TestAop01;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ljq on 2020/5/9 16:05
 */
@RestController
@RequestMapping("testAop")
public class TestAopController {

    @RequestMapping("test01")
    public String testAop(){
        System.out.println("普通方法调用");
        return "普通无切面的方法调用";
    }

    @RequestMapping("test02")
    @TestAop01
    public String testAop01(){
        System.out.println("切面方法调用");
        return "切面的方法调用";
    }

    @RequestMapping("test03")
    @TestAnnotationAop
    public String testAop03(String str){
        try {
            Thread.sleep(5000);
            System.out.println(str);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "切面的方法调用";
    }

}
