package com.yimin.javase.proxy;

/**
 *
 * Created by WuYimin on 2015/6/23.
 */
public class MovealbeCombinationLogProxy implements Moveable {

    private Moveable target;

    public MovealbeCombinationLogProxy(Moveable target) {
        this.target = target;
    }

    @Override
    public void move() {
        System.out.println("start moving");
        target.move();
        System.out.println("end moving");
    }
}
