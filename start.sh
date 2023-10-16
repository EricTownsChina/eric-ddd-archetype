#!/bin/bash

JVM_HEAP="-Xms2048M -Xmx2048M -Xmn512M"
RUN_ARGS="--server.tomcat.acceptorThreadCount=2 --server.tomcat.pollerThreadCount=2"
JAR_NAME="./${APP_NAME}.jar"
LOG_DIR=logs

# 创建日志文件目录
if [ ! -d "${LOG_DIR}" ];then
    mkdir -p "${LOG_DIR}"
fi

## 启动参数
JAVA_OPTS="-Dsun.net.http.allowRestrictedHeaders=true -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -server ${JVM_HEAP} -Xss256K -Xloggc:${LOG_DIR}/${APP_NAME}_gc.log -XX:ParallelGCThreads=8 -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:TargetSurvivorRatio=50 -XX:PretenureSizeThreshold=0 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=70 -XX:+UseCMSInitiatingOccupancyOnly -XX:+CMSClassUnloadingEnabled -XX:-UseAdaptiveSizePolicy -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOG_DIR}/${APP_NAME}_dump.hprof -XX:OnOutOfMemoryError=\"kill -9 %p\" -XX:ErrorFile=${LOG_DIR}/${APP_NAME}_error_%p.log"

RUN_ARGS="${RUN_ARGS} --spring.profiles.active=${ACTIVE}"

echo -e "Starting the ${APP_NAME} ..."

if [ -n "$JAVA_HOME" ]; then
    JAVA="$JAVA_HOME/bin/java"
else
    JAVA=java
fi

## 启动命令
RUN_CMD="${JAVA} ${JAVA_OPTS} -jar ${JAR_NAME} ${RUN_ARGS}"
echo "${RUN_CMD}"

eval "${RUN_CMD}"