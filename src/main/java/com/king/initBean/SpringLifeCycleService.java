package com.king.initBean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * Created by ljq on 2019/3/18 12:11
 */
@Service
public class SpringLifeCycleService implements InitializingBean,DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("SpringLifeCycleService destroy   ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("SpringLifeCycleService start   ");
    }
}
