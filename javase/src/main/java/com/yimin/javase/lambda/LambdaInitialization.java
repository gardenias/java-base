package com.yimin.javase.lambda;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 *
 * Created by WuYimin on 2015/6/19.
 */
public class LambdaInitialization {
    public static void main(String args[]) throws Exception {
        Callable<Void>[] animals = new Callable[] {() -> "Lion", () -> "Crocodile"};

        System.out.println(animals.length);
        Arrays.asList(animals).forEach(c -> {
            try {
                System.out.println(c.call());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Callable<String> animals2 = () -> {
            System.out.println("method body");
            return "Hello";
        };

        // System.out.println(animals.length);
        System.out.println(animals2.call());
    }
}
