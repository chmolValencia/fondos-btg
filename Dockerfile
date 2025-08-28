FROM openjdk:21-jre-slim

WORKDIR /app

COPY target/fondos-btg-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]