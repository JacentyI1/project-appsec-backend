FROM eclipse-temurin:21-jre
COPY target/*.jar app.jar
EXPOSE 27999 
ENTRYPOINT ["java","-jar","app.jar"]

