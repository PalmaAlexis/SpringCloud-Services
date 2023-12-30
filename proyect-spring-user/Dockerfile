FROM openjdk:11-jre-slim-buster
VOLUME /tmp
ADD ./target/proyect-spring-user-0.0.1-SNAPSHOT.jar user-server.jar
ENTRYPOINT ["java","-jar","/user-server.jar" ]