package com.yimin.javase.proxy.jdkproxy.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * java-libs
 * Created by WuYimin on 2015/6/23.
 */
public class LogInterceptor implements Interceptor {

    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("log start");

        Object result = method.invoke(target, args);

        System.out.println("log end");
        return result;
    }

    @Override
    public Object warp(Object target) {
        this.target = target;

        Class<?> type = target.getClass();
        return Proxy.newProxyInstance(type.getClassLoader(), type.getInterfaces(),
                this);
    }
}
