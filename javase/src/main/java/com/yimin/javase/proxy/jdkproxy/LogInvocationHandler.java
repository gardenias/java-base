package com.yimin.javase.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * java-libs
 * Created by WuYimin on 2015/6/23.
 */
public class LogInvocationHandler implements InvocationHandler {
    private Object target;

    public LogInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start");
        Object result = method.invoke(target, args);
        System.out.println("end");
        return result;
    }
}
