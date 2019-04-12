FROM swr.cn-north-1.myhuaweicloud.com/hwcse/dockerhub-java:8-jre-alpine

RUN mkdir -p /carstore/product-service/lib

COPY target/product-service-0.0.1.jar /carstore/product-service/

COPY target/lib /carstore/product-service/lib

ENTRYPOINT java -jar /carstore/product-service/product-service-0.0.1.jar