package com.king.controller.exceptionhandler;

import com.alibaba.fastjson.JSONObject;
import com.king.util.domain.ResultDTO;
import com.king.util.log.LogUtil;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

/**
 * Created by ljq on 2021-6-30 10:23
 */
@ControllerAdvice
public class RestCtrlExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResultDTO handlerException(RuntimeException e, HandlerMethod handlerMethod){
        LogUtil.error("统一异常（Exception）处理"+handlerMethod.getShortLogMessage(),e);
        ResultDTO ret = new ResultDTO();
        ret.setErrMsg(e.getMessage());
        ret.setErrCode("500");
        return ret;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResultDTO handlerException(IllegalArgumentException e){
        ResultDTO ret = new ResultDTO();
        ret.setErrMsg(e.getMessage());
        ret.setErrCode("500");
        return ret;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResultDTO handlerException(MethodArgumentNotValidException e){
        ResultDTO ret = new ResultDTO();
        ret.setErrMsg(e.getMessage().substring(e.getMessage().lastIndexOf("[")));
        ret.setErrCode("500");
        return ret;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.OK)
    public ResultDTO handlerException(Exception e){
        ResultDTO ret = new ResultDTO();
        ret.setErrMsg(e.getMessage());
        ret.setErrCode("500");
        return ret;
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({BindException.class}) //指定拦截异常的类型
    Object bindExceptionHandler(BindException e) {
        LogUtil.error("请求参数错误",e);
        JSONObject jsonObject = new JSONObject();
        List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
        StringBuffer sb = new StringBuffer();
        fieldErrors.forEach(fieldError ->{
                    jsonObject.put(fieldError.getField(), fieldError.getDefaultMessage());
                    sb.append(fieldError.getDefaultMessage());
                    sb.append(";");
                }
        );
        ResultDTO ret = new ResultDTO();
        ret.setErrMsg(sb.toString());
        return ret;
    }

    /**
     * @RequestParam注解 验证导致出现的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler({MissingServletRequestParameterException.class}) //指定拦截异常的类型
    Object missingServletRequestParameterException(MissingServletRequestParameterException e) {
        LogUtil.error("缺少请求参数",e);
        ResultDTO ret = new ResultDTO();
        ret.setErrMsg(e.getMessage());
        return ret;
    }

}
