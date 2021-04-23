FROM docker.io/openjdk:8u191-jdk-alpine3.9
VOLUME /tmp
ADD *.jar app.jar
EXPOSE 9090
ENTRYPOINT [ "java", "-jar", "/app.jar"]