package com.yimin.javalibs.baiduapi;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by WuYimin on 2015/3/18.
 */
public interface ServiceOut {
    default void print(PrintStream out){

    }

    OutputStream getOuter();
}
