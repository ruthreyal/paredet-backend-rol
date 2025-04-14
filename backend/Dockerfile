FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY backend/target/ParedetApp-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-jar", "app.jar"]
