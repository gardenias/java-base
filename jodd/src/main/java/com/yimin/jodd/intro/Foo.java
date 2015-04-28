package com.yimin.jodd.intro;


import java.io.Serializable;
import java.util.Date;

/**
 * User: wuyimin
 * Date: 13-12-4
 */
public class Foo implements Serializable {
    private static final long serialVersionUID = 347113318026714071L;

    private String name;
    private String sex;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
