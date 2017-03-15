package com.leetcode.solution;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

/**
 * @author yimin
 */
public class CountingBitsTest {
    CountingBits countingBits;

    @Before
    public void setUp() throws Exception {
        countingBits = new CountingBits();
    }

    @Test
    public void testCountBits() throws Exception {
        int num = 5;
        int[] bits = countingBits.countBits(num);
        System.out.println("bits = " + Arrays.toString(bits));

        int i = 0;
        assertEquals(6, bits.length);
        assertEquals(0, bits[i++]);
        assertEquals(1, bits[i++]);
        assertEquals(1, bits[i++]);
        assertEquals(2, bits[i++]);
        assertEquals(1, bits[i++]);
        assertEquals(2, bits[i++]);
    }
}
