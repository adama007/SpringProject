FROM openjdk:17
EXPOSE 8080
ADD target/SERVER.jar SERVER.jar
ENTRYPOINT ["java","-jar","/SERVER.jar"]
