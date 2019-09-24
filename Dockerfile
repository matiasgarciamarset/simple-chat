FROM openjdk:8-jre-slim

EXPOSE 8080

RUN mkdir /application

COPY build/libs/*.jar /application/asapp.challenge.jar

ENTRYPOINT ["java" ,"-jar","/application/asapp.challenge.jar"]