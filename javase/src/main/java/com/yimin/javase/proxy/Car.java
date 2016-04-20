package com.yimin.javase.proxy;

import java.util.Random;

/**
 *
 * Created by WuYimin on 2015/6/23.
 */
public class Car implements Moveable {
    @Override
    public void move() {
        int moveTimeMinutes = Math.abs(new Random().nextInt(20));
        System.out.print("Car moving ");
        while (moveTimeMinutes-- > 0) {
            try {
                Thread.sleep(1000);//
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print(".");
        }
        System.out.println();
    }
}
