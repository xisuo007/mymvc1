package com.king.controller.controlleradvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author ljq
 * @Date 2019/11/1 11:46
 * 进行全局controller增强处理
 * 1.全局异常处理
 * 2.全局数据绑定
 * 3.全局数据预处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常处理
     * 可以根据不同的异常进行相应的处理
     */
    @ExceptionHandler(Exception.class) //此注解是指定处理某项异常，比如里面指定NullpointerException，那其他异常不会到这个里面处理
    public ModelAndView customException(Exception e){
        ModelAndView mv = new ModelAndView();
        mv.addObject("message",e.getMessage());
        mv.setViewName("myError");
        return mv;
    }


}
