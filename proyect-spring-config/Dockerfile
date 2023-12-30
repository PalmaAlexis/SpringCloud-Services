FROM openjdk:11-jre-slim-buster
VOLUME /tmp
EXPOSE 8888
ADD ./target/proyect-spring-config-0.0.1-SNAPSHOT.jar config-server.jar
ENTRYPOINT ["java","-jar","/config-server.jar" ]