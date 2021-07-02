package com.king.controller.myinterceptor;

import com.king.controller.validation.Valid;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

/**
 * @Author ljq
 * @Date 2019/10/30 11:34
 */
public class ValidationInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     * 基于URL实现的拦截器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("进入拦截器");
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //方法注解级拦截器
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            if (annotation == null) {
                return true;
            }
            /**
             * 1.获取这个注解的方法
             * 2.获取方法中所有的参数
             * 3.判断参数中有没有注解验证
             * 4.有的话进行验证并抛出验证的异常
             */
            Annotation[][] pans = method.getParameterAnnotations();
            System.out.println("方法参数注解数组？："+pans.toString());
            if (pans == null || pans.length == 0) {
                return true;
            }
            int i = 0;
            MethodParameter[] methodParameters = ((HandlerMethod) handler).getMethodParameters();
            methodParameters[0].getParameterName();


            Parameter[] parameters = method.getParameters();
            System.out.println("方法参数数组?;"+methodParameters[0].getParameterName());
            System.out.println(methodParameters.length);
            System.out.println(methodParameters[0]);
            System.out.println(methodParameters.toString());
            System.out.println(methodParameters[0].getParameter().getName());
            System.out.println(parameters[0]);
            Set<ConstraintViolation<Object>> constraintViolations = null;
            for (Annotation[] pan : pans) {
                //第二层遍历的是注解里面的注解    @Validated({Update.class})    这里的Update
                for (Annotation annotation1 : pan) {
                    System.out.println("方法里某一个注解？："+annotation1.toString());
                    if (annotation1 instanceof Valid) {
                        Valid valid = (Valid)annotation1;
                        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
                        Class<?>[] value = valid.groups();
                        if (value == null || value.length == 0) {
                            constraintViolations = validator.validate(parameters[i]);
                        }else {
                            constraintViolations = validator.validate(parameters[i],value);
                            constraintViolations = validator.validate(methodParameters[i],value);
                        }
                    }
                }
                i++;
            }
            if (!CollectionUtils.isEmpty(constraintViolations)) {
                for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                    String message = constraintViolation.getMessage();
                    throw new RuntimeException(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof MethodHandle) {
            MethodHandle handler1 = (MethodHandle) handler;
            Class<? extends MethodHandle> aClass = handler1.getClass();
            ModelMap modelMap = modelAndView.getModelMap();
        }
    }
}
