FROM swr.cn-north-1.myhuaweicloud.com/hwcse/dockerhub-java:8-jre-alpine

RUN mkdir -p /carstore/edge-service/lib

COPY target/edge-service-0.0.1.jar /carstore/edge-service/

COPY target/lib /carstore/edge-service/lib

ENTRYPOINT java -jar /carstore/edge-service/edge-service-0.0.1.jar