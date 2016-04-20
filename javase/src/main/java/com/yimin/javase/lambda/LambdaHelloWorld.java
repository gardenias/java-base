package com.yimin.javase.lambda;

/**
 *
 * Created by WuYimin on 2015/6/19.
 */
public class LambdaHelloWorld {
    interface HelloWorld {
        String hello(String name);
    }

    public static void main(String[] args) {
        HelloWorld helloWorld = (String name) -> { return "Hello " + name; };
        System.out.println(helloWorld.hello("Joe"));
    }
}
