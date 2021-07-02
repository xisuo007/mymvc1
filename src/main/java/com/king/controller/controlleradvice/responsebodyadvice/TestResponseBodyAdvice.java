package com.king.controller.controlleradvice.responsebodyadvice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Created by ljq on 2020/5/12 18:00
 * 此接口有beforeBodyWrite方法，参数body是响应对象response中的响应体，那么我们就可以用此方法来对响应体做一些统一的操作。比如加密，签名等。
 */
@Component
@RestControllerAdvice("com.king.controller")
public class TestResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 响应时添加签名字段
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String bodyStr = JSON.toJSONString(body, SerializerFeature.MapSortField);
        //body是返回的对象，如果要统一返回类，需要进行处理，比如把返回对象统一包装一下返回等，或进行签名等
        if (body instanceof Throwable) {
            //如果返回的是错误类型的数据
        }
        String sign = null;
        if (body != null) {
        }
        return body;
    }
}
