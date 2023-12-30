FROM openjdk:11-jre-slim-buster
VOLUME /tmp
EXPOSE 9411
ADD ./zipkin-server-3.0.0-rc0-exec.jar zipkin-server.jar
ENTRYPOINT ["java","-jar","/zipkin-server.jar"]