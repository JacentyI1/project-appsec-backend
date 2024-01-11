FROM openjdk:21.04-jdk-slim-buster
COPY target/*.jar app.jar
EXPOSE 27999 
ENTRYPOINT ["java","-jar","app.jar"]

