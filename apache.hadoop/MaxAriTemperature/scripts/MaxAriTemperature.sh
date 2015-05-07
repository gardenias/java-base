#!/bin/bash
source ~/.bashrc

##############################################################################
##
##  MaxAriTemperature start up script for Linux
##
##############################################################################

DEFAULT_JVM_OPTS=""

APP_NAME="MaxAriTemperature"

warn ( ) {
    echo "$*"
}

die ( ) {
    echo
    echo "$*"
    echo
    exit 1
}

# Attempt to set APP_HOME
PRG="$0"
SAVED="`pwd`"
cd "`dirname \"$PRG\"`/.." >&-
APP_HOME="`pwd -P`"
cd "$SAVED" >&-

CLASSPATH=$APP_HOME/build/libs/MaxAriTemperature-1.0.jar:$APP_HOME/build/classes/main

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD="$JAVA_HOME/jre/sh/java"
    else
        JAVACMD="$JAVA_HOME/bin/java"
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD="java"
    which java >/dev/null 2>&1 || die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
fi

# Determine the Hadoop command to use to start the job.
if [ -n "$HADOOP_HOME" ] ; then
    if [ -x "$HADOOP_HOME/bin/hadoop" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables

        HADOOPCMD="$HADOOP_HOME/bin/hadoop"
    fi
    if [ ! -x "$HADOOPCMD" ] ; then
        die "ERROR: HADOOP_HOME is set to an invalid directory: $HADOOP_HOME

Please set the HADOOP_HOME variable in your environment to match the
location of your Hadoop installation."
    fi
else
    HADOOPCMD="hadoop"
    which hadoop >/dev/null 2>&1 || die "ERROR: HADOOP_HOME is not set and no 'hadoop' command could be found in your PATH.

Please set the HADOOP_HOME variable in your environment to match the
location of your Hadoop installation."
fi

#-classpath "$CLASSPATH"

# conf file spe
CONF=$APP_HOME/conf/hadoop-localhost.xml
# local data path
INPUT=$APP_HOME/input/ncdc/all
# hdfs path
OUTPUT=/max-ari-temperature-tmp


echo "$HADOOPCMD" \
        jar "$APP_HOME"/repos/MaxAriTemperature-1.0.jar \
        "$INPUT" \
        "$OUTPUT"

exec "$HADOOPCMD" \
        jar "$APP_HOME"/repos/MaxAriTemperature-1.0.jar \
        "$INPUT" \
        "$OUTPUT"