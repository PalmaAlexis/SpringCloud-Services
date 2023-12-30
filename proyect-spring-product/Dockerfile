FROM openjdk:11-jre-slim-buster
VOLUME /tmp
ADD ./target/proyect-spring-product-0.0.1-SNAPSHOT.jar product-server.jar
ENTRYPOINT ["java","-jar","/product-server.jar" ]