package com.king.controller.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author ljq
 * @Date 2019/10/30 9:19
 */
//@Component
//@Aspect
public class ValidationAspect extends BaseAspect{

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void serviceAspect(){}

    @Before("serviceAspect()")
    public void doBefore(JoinPoint joinPoint){
        System.out.println("进入切面方法");
        Method method = getMethod(joinPoint);
        if (method == null) {
            return;
        }
        //获得待校验参数
        List<ValidBO> validBOList = new ArrayList<>();
        try {
            //获取切到方法的参数列表的注解数组
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (parameterAnnotations == null || parameterAnnotations.length == 0) {
                return;
            }
            int i = 0;
            Object[] args = joinPoint.getArgs();
            for (Annotation[] parameterAnnotation : parameterAnnotations) {
                for (Annotation annotation : parameterAnnotation) {
                    if (annotation instanceof Valid) {
                        Valid validated = (Valid) annotation;
                        System.out.println("对比要验证的参数=======切面传入=========");
                        System.out.println(args[i]);
                        validBOList.add(new ValidBO(args[i],validated));
                        break;
                    }
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        validate(validBOList);
    }

    private void validate(List<ValidBO> validBOS){
        if (CollectionUtils.isEmpty(validBOS)) {
            return;
        }
        for (ValidBO validBO : validBOS) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            Class<?>[] value = validBO.getValidated().groups();
            if (validBO.getForValid() != null && validBO.getForValid() instanceof Collection) {
                System.out.println(validBO.getForValid().getClass().getName());
                for (Object validObj : (Collection) validBO.getForValid()) {
                    System.out.println("切面传入的验证参数列表中的具体参数============="+validObj.toString());
                    validate(value,validObj,validator);
                }
            }else {
                validate(value,validBO.getForValid(),validator);
            }
        }
    }

    private void validate(Class[] group,Object obj,Validator validator){
        Set<ConstraintViolation<Object>> constraintViolations = null;
        if (group == null || group.length == 0) {
            constraintViolations = validator.validate(obj);
        }else {
            constraintViolations = validator.validate(obj,group);
        }
        System.out.println("进入切面验证最后结果================："+obj.toString());
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            String message = constraintViolation.getMessage();
            throw new RuntimeException(message);
        }
    }


}
