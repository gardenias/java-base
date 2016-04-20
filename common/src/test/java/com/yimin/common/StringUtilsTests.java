package com.yimin.common;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * @author yimin
 */
public class StringUtilsTests {
    @Test
    public void testReplacePattern() throws Exception {
        String s1 = "#- #-# test ### test ### ";
        System.out.println("StringUtils.replacePattern(\"" + s1 + "\",\"[#-]+\",\"\")="
                + StringUtils.replacePattern(s1, "^[#-]+", ""));
    }

    @Test
    public void testMatcherGroup() throws Exception {
        String re = "create_([a-z]+).sql";
        String str = "create_mobilebasic.sql";

        Pattern p = Pattern.compile(re);
        Matcher m = p.matcher(str);
        assertTrue(m.find());
        assertThat(m.group(1), is("mobilebasic"));
    }

    @Test
    public void testUseDataBasePattern() throws Exception {
        Pattern useDataBasePattern = Pattern.compile("[(USE)(use)]{1}[ ]+([a-z]+);");
        Matcher m = useDataBasePattern.matcher("USE   mobileoperatedata;");
        assertTrue(m.find());
        assertThat(m.group(1), is("mobileoperatedata"));
        m = useDataBasePattern.matcher("use   mobileoperatedata;");
        assertTrue(m.find());
        assertThat(m.group(1), is("mobileoperatedata"));
    }

    @Test
    public void testCreateTablePattern() throws Exception {
        Pattern createTable = Pattern.compile("[(CREATE TABLE)(create table)]{1}[ ]+`?([a-z_0-9]+)`?[ ]*\\([ ]*");

        Matcher m = createTable.matcher("create table daily_alive (");
        assertTrue(m.find());
        assertThat(m.group(1), is("daily_alive"));
        m = createTable.matcher("CREATE TABLE    `daily_alive`       (   ");
        assertTrue(m.find());
        assertThat(m.group(1), is("daily_alive"));

        m = createTable.matcher("CREATE TABLE `crashtracev2` (");
        assertTrue(m.find());
        assertThat(m.group(1), is("crashtracev2"));
        m = createTable.matcher("create table `real_ios_device`(");
        assertTrue(m.find());
        assertThat(m.group(1), is("real_ios_device"));
    }

    @Test
    public void testDropTablePattern() throws Exception {
        Pattern dropTable = Pattern.compile("[(DROP TABLE IF EXISTS)|(drop table if exists)]{1}[ ]+`?([a-z_]+)`?;$");
        Matcher m = dropTable.matcher("DROP TABLE IF EXISTS `activitytrace`;");
        assertTrue(m.find());
        assertThat(m.group(1), is("activitytrace"));
        m = dropTable.matcher("DROP TABLE IF EXISTS      activitytrace;");
        assertTrue(m.find());
        assertThat(m.group(1), is("activitytrace"));
        m = dropTable.matcher("CREATE DATABASE mobilebasic;");
        assertFalse(m.find());
    }

    @Test
    public void testCommendLinePattern() throws Exception {
        Pattern commentPrefixDash = Pattern.compile("^(--)[ ]+(.*)");
        Pattern commentPrefixHashSymbol = Pattern.compile("^#+[ ]*(.*)");
        Matcher m = commentPrefixDash.matcher("-- is a normal comment line");
        assertTrue(m.find());
        assertThat(m.group(2), is("is a normal comment line"));
        m = commentPrefixDash.matcher("--is not a normal comment line");
        assertFalse(m.find());
        m = commentPrefixDash.matcher("-- ");
        assertFalse(m.find());
        System.out.println("m.group(2) = " + m.group(1));

        m = commentPrefixHashSymbol.matcher("#is a comment line start by #");
        assertTrue(m.find());
        assertThat(m.group(1), is("is a comment line start by #"));

        m = commentPrefixHashSymbol.matcher("###is a comment line start by ###");
        assertTrue(m.find());
        assertThat(m.group(1), is("is a comment line start by ###"));

        m = commentPrefixHashSymbol.matcher("###  is a comment line start by ###  ");
        assertTrue(m.find());
        assertThat(m.group(1), is("is a comment line start by ###  "));
    }

    @Test
    public void testStatementEnding() throws Exception {
        Pattern statementEnding = Pattern.compile(";$");
        Matcher m = statementEnding.matcher("  COMMENT = '按域名统计http请求的平均响应时间；统计最小粒度为天';");
        assertTrue(m.find());
    }
}
