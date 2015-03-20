package com.yimin.algorithm;

/**
 * Created by WuYimin on 2015/3/20.
 */


public class MouseMaze {
    private int startI, startJ; // 入口
    private int endI, endJ; // 出口
    private boolean success = false;

    public static void main(String[] args) { // 迷宫2表示墙 ，@表示出口和入口，0表示可走
        int[][] maze = {{2, 2, 2, 2, 2, 2, 2}, {1, 0, 0, 0, 0, 0, 2},
                {2, 0, 2, 0, 2, 0, 2}, {2, 0, 0, 0, 0, 2, 2},
                {2, 2, 0, 2, 0, 2, 2}, {2, 0, 0, 0, 0, 0, 1},
                {2, 2, 2, 2, 2, 2, 2}};

        System.out.println("显示迷宫：");
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 2) {
                    System.out.print("██");
                } else if (maze[i][j] == 1) {
                    System.out.print(" @");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }

        MouseMaze mouse = new MouseMaze();
        mouse.setStart(1, 1);
        mouse.setEnd(5, 5);
        if (!mouse.go(maze)) {
            System.out.println("\n没有找到出口！");
        } else {
            System.out.println("\n找到出口！");
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    if (maze[i][j] == 2) {
                        System.out.print("██");
                    } else if (maze[i][j] == 1) {
                        System.out.print("$$");
                    } else {
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
        }
    }

    public void setStart(int i, int j) {
        this.startI = i;
        this.startJ = j;
    }

    public void setEnd(int i, int j) {
        this.endI = i;
        this.endJ = j;
    }

    public boolean go(int[][] maze) {
        return visit(maze, startI, startJ);
    }

    private boolean visit(int[][] maze, int i, int j) {
        System.out.printf("maze[%d][%d]=%d\n",i,j,maze[i][j]);
        if (maze[i][j] == 2) return false;
        if (maze[i][j] == 0) maze[i][j] = 1;
        //开始写代码，递归实现老鼠找迷宫路线的功能
        return ((i == endI && j == endJ))
                || (i - 1 < 1 ? false : visit(maze, i - 1, j))
                || i + 1 > 5 ? false : visit(maze, i + 1, j)
                || (( j + 1 > 5) ? false : visit(maze, i, j + 1))
                || ((j - 1 < 1) ? false : visit(maze, i, j - 1));
    }

}

