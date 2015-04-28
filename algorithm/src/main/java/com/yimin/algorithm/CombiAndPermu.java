package com.yimin.algorithm;

/**
 * Created by WuYimin on 2015/3/20.
 */
public class CombiAndPermu {
    /**
     * @param steps      int[]
     * @param used       int[]
     * @param level      int[]
     * @param condicates int[]
     * @return int
     */
    final int constructCondicates(final int[] steps, final int[] used,
                                  final int level, final int[] condicates) {
        //No.1
        //开始写代码，用1、2、2、3、4、5六个数进行排列组合，
        // 例如122345、543221等，且满足3、5不相邻，4不在第三位，打印出所有的排列组合
        //end_code
        return 0;
    }

    /**
     *
     */
    private static final int THRESHOLD = 6;

    /**
     * @param steps int[]
     * @param used  int[]
     * @param level int[]
     * @param total int[]
     */
    final void doGen(final int[] steps, final int[] used,
                     final int level, final int[] total) {
        if (level == THRESHOLD) {
            for (int i = 0; i < THRESHOLD; i++) {
                System.out.print(steps[i] + " ");
            }
            System.out.println();
            total[0]++;
            return;
        }
        int[] condicates = new int[THRESHOLD];
        int cn = constructCondicates(steps, used, level, condicates);
        for (int k = 0; k < cn; k++) {
            int todo = condicates[k];
            steps[level] = todo;
            used[todo - 1]++;
            doGen(steps, used, level + 1, total);
            used[todo - 1]--;
        }
    }

    /**
     * @param args String[]
     */
    public static void main(final String[] args) {
        int[] steps = new int[THRESHOLD];
        int[] used = new int[THRESHOLD];
        int[] total = new int[THRESHOLD];
        new CombiAndPermu().doGen(steps, used, 0, total);
        System.out.println("Totally:" + total[0]);
    }
}
