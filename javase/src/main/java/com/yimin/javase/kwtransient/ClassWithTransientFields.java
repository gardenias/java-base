package com.yimin.javase.kwtransient;

import java.io.Serializable;

/**
 *
 * Created by WuYimin on 2015/5/25.
 */
public class ClassWithTransientFields implements Serializable {
    private static final long serialVersionUID = 4394528758518878987L;
    private String name = "DefaultName";
    private transient String sex = "DefaultSex";

    public ClassWithTransientFields() {
        super();
    }

    public ClassWithTransientFields(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return "ClassWithTransientFields{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}
