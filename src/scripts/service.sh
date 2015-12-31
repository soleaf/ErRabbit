#!/bin/bash

THIS_SCRIPT=$0
COMMAND=$1

PROGRAM_NAME=ErRabbit
PROGRAM_OUTPUT_FILE=/dev/null

JAR_PATH=ErRabbitServer-1.1.0-SNAPSHOT.war
JAVA_OPTS="-Dfile.encoding=UTF8"
SPRING_PROPERTIES=settings.properties
PID_FILE=errabbit.pid

function start() {
    if [ -e $PID_FILE ]
    then
        echo "Already started!"
        exit 1
    fi

    echo "Starting ${PROGRAM_NAME}..."
    nohup java -jar $JAVA_OPTS $JAR_PATH --spring.config.location=file:$SPRING_PROPERTIES > /dev/null &
}

function stop() {
    if ! [ -e $PID_FILE ]
    then
        echo "Already stopped!"
        exit 1
    fi

    echo "Stopping ${PROGRAM_NAME}..."

    kill -TERM `cat $PID_FILE`
}

function usage() {
    echo "Usage: $THIS_SCRIPT <start|status|stop>"
    exit 1
}

function status(){
    if [ -e $PID_FILE ]
    then
        echo "Started"
    else
        echo "Stoped"
    fi
    exit 1
}

if [ "$COMMAND" == "start" ]
then
    start
    exit 0
elif [ "$COMMAND" == "stop" ]
then
    stop
    exit 0
elif [ "$COMMAND" == "status" ]
then
    status
    exit 0
fi


usage