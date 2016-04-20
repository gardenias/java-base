package com.yimin.javase.proxy;

import org.joda.time.DateTime;

/**
 *
 * Created by WuYimin on 2015/6/23.
 */
public class MovealbeCombinationTimerProxy implements Moveable {

    private Moveable target;

    public MovealbeCombinationTimerProxy(Moveable target) {
        this.target = target;
    }

    @Override
    public void move() {
        DateTime start = DateTime.now();
        Long startTime = start.getMillis();
        System.out.printf("startTime %s\n", start.toString("yyyy-HH-mm hh:dd:mm"));

        target.move();

        DateTime end = DateTime.now();
        Long endTime = end.getMillis();
        System.out.printf("endTime %s,duration %d millis\n", start.toString("yyyy-HH-mm hh:dd:mm"), endTime - startTime);

    }
}
