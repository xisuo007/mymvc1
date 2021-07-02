package com.king.controller.controlleradvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ljq
 * @Date 2019/11/1 11:46
 * 进行全局controller增强处理
 * 1.全局异常处理
 * 2.全局数据绑定
 * 3.全局数据预处理
 */
@ControllerAdvice
public class GlobalDataBandHandler {
    /**
     * 全局数据绑定
     */
    @ModelAttribute(name = "md")
    public Map<String,Object> myData(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("age",18);
        map.put("name","张三");
        return map;
    }

}
