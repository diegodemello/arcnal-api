FROM eclipse-temurin:21-jre-alpine

COPY target/arcnal-0.0.1-SNAPSHOT.jar /app/arcnal.jar

CMD ["java", "-jar", "/app/arcnal.jar"]