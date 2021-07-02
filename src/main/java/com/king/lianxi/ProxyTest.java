package com.king.lianxi;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author ljq
 * @Date 2019/10/23 10:07
 */
public class ProxyTest {
    public static void main(String[] args){
        HelloImpl hello = new HelloImpl();
        MyInvocationHandler handler = new MyInvocationHandler(hello);
        //构造代码实例
        Hello proxyHello = (Hello) Proxy.newProxyInstance(HelloImpl.class.getClassLoader(), HelloImpl.class.getInterfaces(), handler);
        proxyHello.sayHello();
    }
}

interface Hello{
    void sayHello();
}

class HelloImpl implements Hello{

    @Override
    public void sayHello() {
        System.out.println("hello");
    }
}

class MyInvocationHandler implements InvocationHandler{
    private Object object;

    public MyInvocationHandler(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invocation sayHello");
        Object result = method.invoke(object, args);
        return result;
    }
}