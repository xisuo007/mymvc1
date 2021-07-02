package com.king.aop.interceptor;

import com.king.aop.annotation.TestAop01;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by ljq on 2020/5/9 15:54
 */
public class TestAopInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            TestAop01 aop01 = method.getMethodAnnotation(TestAop01.class);
            if (aop01 == null) {
                return super.preHandle(request,response,handler);
            }
            System.out.println("拦截器处理切面");
            return true;
        }
        return super.preHandle(request,response,handler);
    }
}
