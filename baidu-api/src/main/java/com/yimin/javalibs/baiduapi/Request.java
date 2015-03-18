package com.yimin.javalibs.baiduapi;

import java.io.IOException;

/**
 * Created by WuYimin on 2015/3/18.
 */
public interface Request {
    ServiceOut perform(String params) throws IOException;
}
