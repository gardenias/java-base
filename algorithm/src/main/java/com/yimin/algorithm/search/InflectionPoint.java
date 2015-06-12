package com.yimin.algorithm.search;

import java.util.Arrays;

/**
 * java-libs
 * Created by WuYimin on 2015/6/12.
 * 整型数组：前半部分升序，后半部分降序
 * 找出拐点的位置
 */
public class InflectionPoint {

    /**
     * @param inflectionArray
     * @return
     */
    public static int inflection(int[] inflectionArray) {
        if (inflectionArray == null || inflectionArray.length == 0) {
            System.out.print("拐点数组为空\n");
            return -1;
        }

        System.out.printf(Arrays.toString(inflectionArray));
        int inflectionPointLocation = 0;
        int start = 0;
        int end = inflectionArray.length - 1;
        if (start == end) {
            inflectionPointLocation = start;
        } else {
            int middle = 0;
            while (start + 1 < end) {
                middle = (end + start) / 2;

                if (inflectionArray[middle] > inflectionArray[middle - 1]) start = middle;
                if (inflectionArray[middle] > inflectionArray[middle + 1]) end = middle;
            }

            inflectionPointLocation = inflectionArray[start] > inflectionArray[end] ? start : end;
        }
        System.out.printf("拐点坐标为：%d,拐点值为:%d\n", inflectionPointLocation, inflectionArray[inflectionPointLocation]);
        return inflectionPointLocation;
    }
}
