FROM swr.cn-north-1.myhuaweicloud.com/hwcse/dockerhub-java:8-jre-alpine

RUN mkdir -p /carstore/user-service/lib

COPY target/user-service-0.0.1.jar /carstore/user-service/

COPY target/lib /carstore/user-service/lib

ENTRYPOINT java -jar /carstore/user-service/user-service-0.0.1.jar