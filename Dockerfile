FROM openjdk:8
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=target/learning-management-system-1.0.0-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]