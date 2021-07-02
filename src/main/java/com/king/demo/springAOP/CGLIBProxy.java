package com.king.demo.springAOP;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Created by ljq on 2019/3/18 15:07
 */
public class CGLIBProxy {
    public void test(){
        System.out.println("正常的方法----");
    }
    public static void main(String[] args){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(CGLIBProxy.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy proxy) throws Throwable {
                System.out.println("CGLIB代理前------------------");
                Object result = proxy.invokeSuper(o, objects);
                System.out.println("CGLIB代理后================");
                return result;
            }
        });
        CGLIBProxy proxy =(CGLIBProxy) enhancer.create();
        proxy.test();
    }

}
