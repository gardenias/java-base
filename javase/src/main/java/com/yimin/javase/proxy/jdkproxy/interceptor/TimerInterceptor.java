package com.yimin.javase.proxy.jdkproxy.interceptor;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.joda.time.DateTime;

/**
 *
 * Created by WuYimin on 2015/6/23.
 */
public class TimerInterceptor implements Interceptor {

    private Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DateTime start = DateTime.now();
        Long startTime = start.getMillis();
        System.out.printf("startTime %s\n", start.toString("yyyy-HH-mm hh:dd:mm"));

        Object result = method.invoke(target, args);

        DateTime end = DateTime.now();
        Long endTime = end.getMillis();
        System.out.printf("endTime %s,duration %d millis\n", start.toString("yyyy-HH-mm hh:dd:mm"), endTime - startTime);

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
