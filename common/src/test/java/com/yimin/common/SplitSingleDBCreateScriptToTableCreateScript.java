package com.yimin.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

/**
 * @author yimin
 */
public class SplitSingleDBCreateScriptToTableCreateScript {

    public static final String basePath = "/Users/yimin/Development/code/oneapm/gerrit/release/das/sql/";
    static Pattern dataBaseCreateSqlFileNamePattern = Pattern.compile("create_([a-z]+).sql");
    static Pattern createDatabasePattern = Pattern.compile("[(CREATE DATABASE)|(create database)]{1}[ ]+([a-z]+);");

    static Pattern statementEnding = Pattern.compile(";$");

    static Pattern commentPrefixDash = Pattern.compile("^(--)[ ]+(.*)");
    static Pattern commentPrefixHashSymbol = Pattern.compile("^#+[ ]*(.*)");

    static Pattern createTablePattern = Pattern
            .compile("[(CREATE TABLE)|(create table)]{1}[ ]+`?([a-z_0-9]+)`?[ ]*\\([ ]*");

    private static final String[] targets = {
            ""
            , "create_mobilebasic.sql"
             , "create_mobileprivatedata.sql"
             , "create_mobileprivateinfo.sql"
             , "create_mobileprivatetrace.sql"
             , "create_mobileoperatedata.sql"
             , "create_mobilestat.sql"
    };
    public static final String outputPath = "tables";
    public static final String utf8 = "UTF-8";

    public static void main(String[] args) throws IOException {

        DBStructure structure = new DBStructure();

        int totalLineStat = 0;
        int totalBlankLineStat = 0;
        int cleanLineStat = 0;

        FileInputStream fileInputStream = null;
        List<String> comments = null;
        Database database = null;
        Table table = null;

        for (String target : targets) {
            if (StringUtils.isBlank(target)) continue;
            File createDBScript = new File(basePath + target);
            if (!createDBScript.exists()) continue;

            String fileName = createDBScript.getName();

            database = structure.pop(new Database(getDBName(fileName)));

            comments = new ArrayList<>();

            try {
                fileInputStream = new FileInputStream(createDBScript);
                List<String> lines = IOUtils.readLines(fileInputStream, utf8);
                Iterator<String> iterator = lines.iterator();
                while (iterator.hasNext()) {
                    String line = StringUtils.trim(iterator.next());
                    totalLineStat++;

                    if (StringUtils.isBlank(line)) {
                        totalBlankLineStat++;
                        continue;
                    }

                    if (isCommentLine(line)) {
                        comments.add(line);
                        continue;
                    }

                    if (isCleanLines(line)) {
                        // clean drop statement
                        cleanLineStat++;
                        continue;
                    }
                    if (isCreateTableScriptLine(line)) {
                        table = database.pop(new Table(getTableNameByCreateStatement(line)));
                        table.appendCreateLine(line);
                        while (!isStatementEnding(line) && iterator.hasNext()) {
                            line = iterator.next();
                            totalLineStat++;
                            table.appendCreateLine(line);
                        }
                        if (CollectionUtils.isNotEmpty(comments)) {
                            table.setComments(comments);
                            comments.clear();
                        }
                        continue;
                    }
                    if (isCreateDatabaseScriptLine(line) && isStatementEnding(line)) {
                        database = structure.pop(new Database(getDatabaseNameByCreateStatement(line)));
                        database.appendCreateLine(line);
                        if (CollectionUtils.isNotEmpty(comments)) {
                            database.setComments(comments);
                            comments.clear();
                        }
                        continue;
                    }
                    // other liens,default append to db script
                    database.appendCreateLine(line);

                }
            } finally {
                if (null != fileInputStream) {
                    IOUtils.closeQuietly(fileInputStream);
                }
            }
        }
        System.out.println("totalLineStat = " + totalLineStat);
        System.out.println("totalBlankLineStat = " + totalBlankLineStat);
        System.out.println("cleanLineStat = " + cleanLineStat);
        System.out.println("useful script lines number  = " + (totalLineStat - totalBlankLineStat - cleanLineStat));

        // structure.print();
        structure.persist(basePath + outputPath, true);
    }

    private static String getDatabaseNameByCreateStatement(String line) {
        Matcher createDatabaseMatcher = createDatabasePattern.matcher(line);
        createDatabaseMatcher.find();
        return createDatabaseMatcher.group(1);
    }

    private static boolean isCreateDatabaseScriptLine(String line) {
        return StringUtils.startsWithIgnoreCase(line, "CREATE DATABASE");
    }

    private static boolean isCommentLine(String line) {
        return StringUtils.startsWithIgnoreCase(line, "--")
                || commentPrefixDash.matcher(line).find()
                || commentPrefixHashSymbol.matcher(line).find();
    }

    private static boolean isStatementEnding(String line) {
        return statementEnding.matcher(line).find();
    }

    private static String getTableNameByCreateStatement(String line) {
        Matcher dropTableMatcher = createTablePattern.matcher(line);
        dropTableMatcher.find();
        return dropTableMatcher.group(1);
    }

    private static boolean isCleanLines(String line) {
        return StringUtils.startsWithIgnoreCase(line, "DROP TABLE IF EXISTS")
                || StringUtils.startsWithIgnoreCase(line, "truncate table");
    }

    private static boolean isCreateTableScriptLine(String line) {
        return createTablePattern.matcher(line).find();
    }

    private static String getDBName(String fileName) {
        Matcher m = dataBaseCreateSqlFileNamePattern.matcher(fileName);
        while (m.find()) {
            return m.group(1);
        }
        return fileName;
    }
}

abstract class Script {
    @Getter
    protected String name;
    @Getter
    protected List<String> comments;
    @Getter
    protected List<String> lines;

    public Script(String name) {
        this.name = name;
        comments = new ArrayList<>();
        lines = new ArrayList<>();
    }

    public Script appendCreateLine(String line) {
        if (StringUtils.containsIgnoreCase(line, "COMMENT") &&
                StringUtils.containsIgnoreCase(
                        line.substring(StringUtils.lastIndexOfIgnoreCase(line, "COMMENT")),
                        "COMMENT")) {
            line = StringUtils.replaceEach(line, new String[] {"\""}, new String[] {"'"});
        }
        add(lines, line);
        return this;
    }

    public void setComments(List<String> comments) {
        this.comments.addAll(comments);
    }

    private void add(List<String> target, String line) {
        target.add(line);
    }

    public int lines() {
        return comments.size() + lines.size();
    }

    public void print() {
        this.getComments().forEach((String comment) -> {
            System.out.printf("\t\t\t%s\n", comment);
        });
        this.getLines().forEach((String createScript) -> {
            System.out.printf("\t\t\t%s\n", createScript);
        });
    }

    public Reader getReader(boolean formatCommentFormat) throws IOException {
        List<String> allLines = Lists.newArrayList();
        if (formatCommentFormat) {
            allLines.addAll(formatCommentFormat(comments));
        } else {
            allLines.addAll(comments);
        }
        allLines.addAll(lines);
        return new IteratorStringReader(allLines.iterator());
    }

    private List<String> formatCommentFormat(List<String> comments) {
        List<String> result = new ArrayList<>();
        result.add("--");
        if (CollectionUtils.isEmpty(comments)) {
            result.add("-- " + getClass().getSimpleName() + " : " + getName());
        } else {
            comments.forEach((String line) -> {
                Matcher dashMatcher = SplitSingleDBCreateScriptToTableCreateScript.commentPrefixDash.matcher(line);
                if (dashMatcher.find()) {
                    result.add("-- " + dashMatcher.group(2));
                    return;
                } else {
                    Matcher hashSymbolMatcher =
                            SplitSingleDBCreateScriptToTableCreateScript.commentPrefixHashSymbol.matcher(line);
                    if (hashSymbolMatcher.find()) {
                        result.add("-- " + hashSymbolMatcher.group(1));
                        return;
                    }
                }
                System.out.println("line = " + line);
            });
        }
        result.add("--");
        return result;
    }

    protected void persist(FileOutputStream fileOutputStream, boolean formatCommentFormat) throws IOException {
        IOUtils.copy(getReader(formatCommentFormat), fileOutputStream);
    }
}

class IteratorStringReader extends Reader {
    private final Iterator<String> it;
    private StringReader current;
    private String separator = "\r";

    public IteratorStringReader(Iterator<String> sources) throws IOException {
        this.it = sources;
        advance();
    }

    private void advance() throws IOException {
        close();
        if (it.hasNext()) {
            current = new StringReader(it.next() + separator);
        }
    }

    @Override
    public long skip(long n) throws IOException {
        Preconditions.checkArgument(n >= 0, "n is negative");
        if (n > 0) {
            while (current != null) {
                long result = current.skip(n);
                if (result > 0) {
                    return result;
                }
                advance();
            }
        }
        return 0;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        if (current == null) {
            return -1;
        }
        int result = current.read(cbuf, off, len);
        if (result == -1) {
            advance();
            return read(cbuf, off, len);
        }
        return result;
    }

    @Override
    public boolean ready() throws IOException {
        return (current != null) && current.ready();
    }

    @Override
    public void close() throws IOException {
        if (current != null) {
            try {
                current.close();
            } finally {
                current = null;
            }
        }
    }
}

class DBStructure {
    @Getter
    private Map<String, Database> databases;

    public DBStructure() {
        databases = new HashMap<>();
    }

    Database pop(Database database) {
        if (databases.containsKey(database.getName())) return databases.get(database.getName());

        databases.put(database.getName(), database);
        return database;
    }

    public void print() {
        final int[] lineStat = {0};
        databases.forEach((String tableName, Database database) -> {
            lineStat[0] += database.lines();
            System.out.printf("\tdatabase:{{%s}}, tables count : %d\n", database.getName(), database.count());
            database.print();
        });
        System.out.println("lineStat = " + lineStat[0]);
    }

    public void persist(String persistLocation, boolean formatCommentFormat) {
        databases.forEach((String tableName, Database database) -> {

            String databasePersistLocation = persistLocation + File.separator + database.getName() + File.separator;
            File databaseScripts = new File(databasePersistLocation);
            try {
                Files.createParentDirs(databaseScripts);
                database.persist(databasePersistLocation, formatCommentFormat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}

class Database extends Script {

    private int count = 0;
    private Map<String, Table> tables;

    public Database(String name) {
        super(name);
        tables = new HashMap<>();
    }

    public Table pop(Table table) {
        if (tables.containsKey(table.getName())) return tables.get(table.getName());

        tables.put(table.getName(), table);
        count++;
        return table;
    }

    public Object count() {
        return count;
    }

    @Override
    public int lines() {
        final int[] lines = {super.lines()};
        tables.forEach((String tableName, Table table) -> {
            lines[0] += table.lines();
        });
        return lines[0];
    }

    public void print() {
        tables.forEach((String tableName, Table table) -> {
            System.out.printf("\t\ttable:{{%s}}\n", table.getName());
            table.print();
        });
        System.out.println("database more scripts:");
        super.print();
    }

    public void persist(String databasePersistLocation, boolean formatCommentFormat) throws IOException {
        File databaseScripts = new File(databasePersistLocation + "database_" + name + ".sql");
        Files.createParentDirs(databaseScripts);
        if (!databaseScripts.exists()) databaseScripts.createNewFile();

        persist(new FileOutputStream(databaseScripts), formatCommentFormat);// output db script

        tables.forEach((String tableName, Table table) -> {
            try {
                table.persist(databasePersistLocation, formatCommentFormat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}

class Table extends Script {

    @Setter
    private String drop;

    public Table(String name) {
        super(name);
    }

    public int lines() {
        return ((null != drop) ? 1 : 0) + super.lines();
    }

    public void persist(String databasePersistLocation, boolean formatCommentFormat) throws IOException {
        File tables = new File(databasePersistLocation + name + ".sql");
        if (!tables.exists()) tables.createNewFile();
        persist(new FileOutputStream(tables), formatCommentFormat);// output db script
    }
}
