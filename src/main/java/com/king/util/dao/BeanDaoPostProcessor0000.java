package com.king.util.dao;

import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by ljq on 2020/3/3 9:27
 */
public class BeanDaoPostProcessor0000 implements BeanPostProcessor {

    @Resource
    ApplicationContext context;
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        injectDao(bean);
        return bean;
    }
    public void injectDao(Object bean){
        try {
            Object target = bean;
            Class<?> targetClass = bean.getClass();
            if (bean instanceof Advised) {
                //判断该bean是不是有注解被切了，是的话获取切到的目标
                TargetSource source = ((Advised) bean).getTargetSource();
                target = source.getTarget();
                targetClass = source.getClass();
            }

            if (target != null && targetClass != null) {
                //获取被切到的类的成员
                Field[] fields = targetClass.getDeclaredFields();
                for (Field field : fields) {
                    //判断属性上有没有自定义的注解
                    if (field.getAnnotation(InjectDAO.class) != null) {
                        Type type = field.getGenericType();
                        //判断属性类型是不是带泛型的
                        if (type instanceof ParameterizedType) {
                            ParameterizedType genericType = (ParameterizedType) type;
                            //获取属性里泛型数组
                            Type[] arguments = genericType.getActualTypeArguments();
                            if (arguments.length > 0) {
                                String name = arguments[0].getTypeName();
                                DAO dao = context.getBean(DAO.class);
                                dao.setPrefix(name);
                                boolean accessible = field.isAccessible();
                                if (!accessible) {
                                    field.setAccessible(true);
                                }
                                field.set(target,dao);
                                field.setAccessible(accessible);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*

@Service
public class ActivityInfoServiceImpl extends  BaseService<ActivityInfo, Integer> implements ActivityInfoService {

    @InjectDAO
    private DAO<ActivityCarModel, Integer> activityCarModelDAO;\
}

*/
