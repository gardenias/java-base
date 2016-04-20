package jodd;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.util.Date;

import jodd.bean.BeanException;
import jodd.bean.BeanTemplateParser;
import jodd.bean.BeanUtil;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: wuyimin
 * Date: 13-12-4
 */
@RunWith(JUnit4.class)
public class PropertyReadAndWrite {
    private static final Logger log = LoggerFactory.getLogger(PropertyReadAndWrite.class);

    String sex;
    String name;
    DateTime birthDay;

    @Before
    public void tearDown() throws Exception {
        sex = "male";
        name = "wuyimin";
        birthDay = DateTime.parse("1985-12-28");
    }

    @Test
    public void testPropertySetAndRead() {
        Foo foo = new Foo();
        BeanUtil.setProperty(foo, "name", name);
        Assert.assertEquals(name, BeanUtil.getProperty(foo, "name"));
    }

    @Test(expected = BeanException.class)
    public void testSetDeclaredFiledBySetMethod() throws Exception {
        Foo foo = new Foo();
        String sex = "male";
        BeanUtil.setProperty(foo, "sex", sex);
    }

    @Test
    public void testDeclaredFiledSetAndRead() throws Exception {
        Foo foo = new Foo();
        BeanUtil.setDeclaredProperty(foo, "sex", sex);
        Assert.assertEquals(sex, BeanUtil.getProperty(foo, "sex"));
        Assert.assertEquals(sex, BeanUtil.getDeclaredProperty(foo, "sex"));
    }

    @Test
    public void testSetBySetterMethodOrDeclaredPropertySetPerformance() throws Exception {
        int statTimes = 10;
        while (statTimes-- >= 0) {
            Foo foo = new Foo();
            int times = 10000000;
            Long startTime = System.currentTimeMillis();
            while (times-- > 0) {
                foo.setName(name);
            }
            Long duration = System.currentTimeMillis() - startTime;
            log.info("set bean's property by foo.setName(name) ten millions times used {} millisecond", duration);

            times = 10000000;
            startTime = System.currentTimeMillis();
            while (times-- > 0) {
                BeanUtil.setProperty(foo, "name", name);
            }
            duration = System.currentTimeMillis() - startTime;
            log.info("set bean's property by jodd BeanUtil.setProperty ten millions times used {} millisecond",
                    duration);

            times = 10000000;
            startTime = System.currentTimeMillis();
            while (times-- > 0) {
                BeanUtil.setDeclaredProperty(foo, "sex", sex);
            }
            duration = System.currentTimeMillis() - startTime;
            log.info("set bean's property by jodd BeanUtil.setDeclaredProperty ten millions times used {} millisecond",
                    duration);
        }
    }

    @Test
    public void testTemplateParser() throws Exception {
        String template = "Hello ${name},sex: ${sex}.";
        Foo foo = new Foo();
        foo.setName("John Doe");
        BeanUtil.setDeclaredProperty(foo, "sex", sex);
        BeanUtil.setDeclaredProperty(foo, "birthday", birthDay.toDate());
        BeanTemplateParser btp = new BeanTemplateParser();
        Assert.assertEquals("Hello John Doe,sex: male.", btp.parse(template, foo));
        assertEquals("1985-12-28", new DateTime(BeanUtil.getDeclaredProperty(foo, "birthday")).toString("yyyy-MM-dd"));
    }
}

class Foo implements Serializable {
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
