package com.king.demo.springAOP;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ljq on 2019/3/15 15:54
 */
public class JDKProxy {

    static interface Subject {
        void exct();
    }

    static class SubjectImpl implements Subject {

        @Override
        public void exct() {
            System.out.println("正常的方法执行。。。");
        }
    }

    static class JDKProxyTest implements InvocationHandler {
        private Subject target;

        public JDKProxyTest(Subject target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("代理之前+++++++++++++");
            Object invoke = method.invoke(target, args);
            System.out.println("代理之后----------");
            return invoke;
        }

    }

    public static void main(String[] args) {
        Subject subject = new SubjectImpl();
        Subject proxySubject = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), new JDKProxyTest(subject));
        proxySubject.exct();
    }
}
