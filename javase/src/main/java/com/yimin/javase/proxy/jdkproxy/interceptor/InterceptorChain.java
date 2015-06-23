package com.yimin.javase.proxy.jdkproxy.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * java-libs
 * Created by WuYimin on 2015/6/23.
 */
public class InterceptorChain {
    private List<Interceptor> interceptors = new ArrayList<>();

    public void addInterceptor(Interceptor... interceptors) {
        if (interceptors != null && interceptors.length > 0)
            this.interceptors.addAll(Arrays.asList(interceptors));
    }

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : interceptors) {
            target = interceptor.warp(target);
        }
        return target;
    }
}
