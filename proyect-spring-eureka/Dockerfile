FROM openjdk:11-jre-slim-buster
VOLUME /tmp
EXPOSE 8761
ADD ./target/proyect-spring-eureka-0.0.1-SNAPSHOT.jar eureka-server.jar
ENTRYPOINT ["java","-jar","/eureka-server.jar" ]