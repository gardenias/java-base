package proxymode;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import org.junit.Test;

import com.yimin.javase.proxy.Car;
import com.yimin.javase.proxy.Moveable;
import com.yimin.javase.proxy.MovealbeCombinationLogProxy;
import com.yimin.javase.proxy.MovealbeCombinationTimerProxy;
import com.yimin.javase.proxy.jdkproxy.LogInvocationHandler;
import com.yimin.javase.proxy.jdkproxy.TimerInvocationHandler;

/**
 *
 * Created by WuYimin on 2015/6/23.
 */
public class MovealbeCombinationTimerProxyTest {
    @Test
    public void testProxyMove() throws Exception {
        Moveable moveable = new MovealbeCombinationTimerProxy(new Car());
        moveable.move();
    }

    @Test
    public void testMultiProxyMove() throws Exception {
        System.out.println("log -> timer proxy");
        Moveable moveable = new MovealbeCombinationLogProxy(new MovealbeCombinationTimerProxy(new Car()));
        moveable.move();

        System.out.println("timer -> log proxy");
        moveable = new MovealbeCombinationTimerProxy(new MovealbeCombinationLogProxy(new Car()));
        moveable.move();
    }

    @Test
    public void testJdkProxyMove() throws Exception {
        Car target = new Car();
        InvocationHandler logInvocationHandler = new LogInvocationHandler(target);
        TimerInvocationHandler timerInvocationHandler = new TimerInvocationHandler(target);

        System.out.println("== 1 ");
        Moveable proxySubject = (Moveable) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), logInvocationHandler);
        proxySubject.move();

        System.out.println("== 2 ");
        proxySubject = (Moveable) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), timerInvocationHandler);
        proxySubject.move();

        //无法达到串联两个 invocationHandler处理器
        System.out.println("== 3 ");
        Object proxyObject = Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), timerInvocationHandler);
        proxyObject = Proxy.newProxyInstance(proxyObject.getClass().getClassLoader(),
                proxyObject.getClass().getInterfaces(), logInvocationHandler);
        ((Moveable) proxyObject).move();
    }
}
