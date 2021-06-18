FROM openjdk:13-alpine

EXPOSE 8080

# We don't want to run the carpooling as a root for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
