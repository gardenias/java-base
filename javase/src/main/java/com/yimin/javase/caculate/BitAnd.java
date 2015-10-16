package com.yimin.javase.caculate;

import java.util.Random;

/**
 * Created by yimin on 15/7/22.
 */
public class BitAnd {
    public static void main(String[] args) {
        int times = 0;
        for (; ; ) {
            int source = randomInt();
            int modResult = mod(source);

            System.out.println("mod(" + source + ") = " + modResult);

            if (times++ > 100) break;
        }
    }

    static final Random random = new Random(System.currentTimeMillis());

    private static Integer randomInt() {
        return Math.abs(random.nextInt());
    }

    private static int mod(int i) {
        return (i + 1) & 7;
    }
}
