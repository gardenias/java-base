package com.yimin.javase.proxy.jdkproxy.interceptor;

import java.lang.reflect.InvocationHandler;

/**
 * java-libs
 * Created by WuYimin on 2015/6/23.
 */
public interface Interceptor extends InvocationHandler {
    Object warp(Object target);
}
