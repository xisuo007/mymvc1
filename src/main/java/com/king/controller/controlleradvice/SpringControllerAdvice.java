package com.king.controller.controlleradvice;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @Author ljq
 * @Date 2019/11/1 11:46
 * 进行全局controller增强处理
 * 1.全局异常处理
 * 2.全局数据绑定
 * 3.全局数据预处理
 */
@ControllerAdvice
public class SpringControllerAdvice {

    /**
     * 全局数据预处理
     */
    @InitBinder("a")
    public void globalInitBinder(WebDataBinder binder){
        binder.setFieldDefaultPrefix("a.");
    }
}
