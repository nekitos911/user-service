FROM adoptopenjdk:8-jre-hotspot
ARG JAR_FILE=user-service-impl/target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]