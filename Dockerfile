# 基础镜像, 这个jre镜像是最小的。。。
FROM openjdk:8-jre-alpine3.7

# 维护人
MAINTAINER EricTownsChina

# 工作路径
WORKDIR /opt/

# 复制启动脚本到/opt/
COPY ./start.sh ./target/app.jar /opt/

# 环境变量
ENV LANG=zh_CN.UTF-8 \
    APP_NAME=app \
    ACTIVE=dev

# openjdk:8-jre-alpine3.7添加bash支持
RUN apk update && \
    apk add bash && \
    rm -rf /var/cache/apk/* && \
    chmod 755 /opt/start.sh

# 开放端口
EXPOSE 8007

CMD ["/opt/start.sh"]

