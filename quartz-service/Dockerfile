FROM swr.cn-north-1.myhuaweicloud.com/hwcse/dockerhub-java:8-jre-alpine

RUN mkdir -p /carstore/quartz-service/lib

COPY target/quartz-service-0.0.1.jar /carstore/quartz-service/

COPY target/lib /carstore/quartz-service/lib

ENTRYPOINT java -jar /carstore/quartz-service/quartz-service-0.0.1.jar