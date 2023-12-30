FROM openjdk:11-jre-slim-buster
VOLUME /tmp
EXPOSE 9100
ADD ./target/proyect-spring-oauth-0.0.1-SNAPSHOT.jar oauth-server.jar
ENTRYPOINT ["java","-jar","/oauth-server.jar" ]