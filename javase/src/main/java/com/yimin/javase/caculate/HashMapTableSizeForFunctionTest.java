package com.yimin.javase.caculate;

/**
 * Created by yimin on 15/7/24.
 */
public class HashMapTableSizeForFunctionTest {

    public static void main(String[] args) {
        System.out.println(tableSizeFor(6));
        System.out.println(tableSizeFor(37));
    }

    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 保证计算出来的 cap 总是 2 的幂次方
     *
     * @param cap
     * @return
     */
    static final int tableSizeFor(int cap) {
        System.out.println(cap);
        int n = cap - 1;
        System.out.println(n);
        n |= n >>> 1;
        System.out.println(n);
        n |= n >>> 2;
        System.out.println(n);
        n |= n >>> 4;
        System.out.println(n);
        n |= n >>> 8;
        System.out.println(n);
        n |= n >>> 16;
        System.out.println(n);
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
}
