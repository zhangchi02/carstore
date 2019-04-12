FROM swr.cn-north-1.myhuaweicloud.com/hwcse/dockerhub-java:8-jre-alpine

RUN mkdir -p /carstore/dao-service/lib

COPY target/dao-service-0.0.1.jar /carstore/dao-service/

COPY target/lib /carstore/dao-service/lib

ENTRYPOINT java -jar /carstore/dao-service/dao-service-0.0.1.jar