FROM eclipse-temurin:21-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} carRental.jar
ENTRYPOINT ["java","-jar","carRental.jar"]
EXPOSE 8080