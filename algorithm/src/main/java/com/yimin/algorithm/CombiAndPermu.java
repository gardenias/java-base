package com.yimin.algorithm;

/**
 * Created by WuYimin on 2015/3/20.
 */
public class CombiAndPermu{
    int constructCondicates(int steps[], int[] used, int level, int[] condicates) {
        //No.1
        //开始写代码，用1、2、2、3、4、5六个数进行排列组合，例如122345、543221等，且满足3、5不相邻，4不在第三位，打印出所有的排列组合



        //end_code
        return 0;
    }
    void doGen(int steps[], int used[], int level, int[] total) {
        if (level == 6) {
            for (int i = 0; i < 6; i++) {
                System.out.print(steps[i] + " ");
            }
            System.out.println();
            total[0]++;
            return;
        }
        int condicates[] = new int[6];
        int cn = constructCondicates(steps, used, level, condicates);
        for (int k = 0; k < cn; k++) {
            int todo = condicates[k];
            steps[level] = todo;
            used[todo - 1]++;
            doGen(steps, used, level + 1, total);
            used[todo - 1]--;
        }
    }
    public static void main(String[] args) {
        int[] steps = new int[6];
        int[] used = new int[6];
        int total[] = new int[1];
        new CombiAndPermu().doGen(steps, used, 0, total);
        System.out.println("Totally:" + total[0]);
    }
}
