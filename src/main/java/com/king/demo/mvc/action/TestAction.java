package com.king.demo.mvc.action;

import com.king.demo.service.NameService;
import com.king.demo.service.TestService;
import com.king.framework.annotation.MYAutowired;
import com.king.framework.annotation.MYController;
import com.king.framework.annotation.MYRequestMapping;
import com.king.framework.annotation.MYRequestParam;
import com.king.framework.servlet.MyModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ljq on 2018/11/29 14:10
 */
@MYController
@MYRequestMapping("/web")
public class TestAction {
    @MYAutowired
    private TestService testService;
    @MYAutowired("myName")
    private NameService nameService;

    @MYRequestMapping("/query/.*.json")
    public MyModelAndView query(HttpServletRequest request, HttpServletResponse response,
                                @MYRequestParam(value = "name",required = false)String name,
                                @MYRequestParam("addr")String addr){
        Map<String,Object> model = new HashMap<>();
        model.put("name",name);
        model.put("addr",addr);
        return new MyModelAndView("first.pgml",model);
    }

    @MYRequestMapping("/add.json")
    public MyModelAndView add(HttpServletRequest request, HttpServletResponse response){
        out(response,"this is my mvc");
        return null;
    }

    public void out(HttpServletResponse response,String str){
        try {
            response.getWriter().write(str);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
