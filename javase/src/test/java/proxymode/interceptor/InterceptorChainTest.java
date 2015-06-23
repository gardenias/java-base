package proxymode.interceptor;

import com.yimin.javase.proxy.Car;
import com.yimin.javase.proxy.Moveable;
import com.yimin.javase.proxy.jdkproxy.interceptor.Interceptor;
import com.yimin.javase.proxy.jdkproxy.interceptor.InterceptorChain;
import com.yimin.javase.proxy.jdkproxy.interceptor.LogInterceptor;
import com.yimin.javase.proxy.jdkproxy.interceptor.TimerInterceptor;
import org.junit.Test;

/**
 * java-libs
 * Created by WuYimin on 2015/6/23.
 */
public class InterceptorChainTest {
    @Test
    public void testMultiInterceptor() throws Exception {
        Car target = new Car();
        Interceptor  logInterceptor= new LogInterceptor();
        Object proxyObject = logInterceptor.warp(target);

        Interceptor timerInterceptor = new TimerInterceptor();
        proxyObject = timerInterceptor.warp(proxyObject);

        Moveable moveable = (Moveable) proxyObject;
        moveable.move();
    }

    @Test
    public void testInterceptorChain() throws Exception {

        Car target = new Car();
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(
                new LogInterceptor(),
                new TimerInterceptor(),
                new TimerInterceptor(),
                new LogInterceptor()
        );

        Moveable moveable = (Moveable) interceptorChain.pluginAll(target);
        moveable.move();
    }
}